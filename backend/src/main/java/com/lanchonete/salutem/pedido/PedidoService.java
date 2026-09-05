package com.lanchonete.salutem.pedido;

import com.lanchonete.salutem.bebida.BebidaService;
import com.lanchonete.salutem.bebida.model.BebidaEntity;
import com.lanchonete.salutem.hamburguer.HamburguerService;
import com.lanchonete.salutem.hamburguer.model.HamburguerEntity;
import com.lanchonete.salutem.pedido.exception.PedidoBebidaAndHamburguerEmptyException;
import com.lanchonete.salutem.pedido.exception.PedidoItemIdsNotFoundException;
import com.lanchonete.salutem.pedido.exception.PedidoNotFoundException;
import com.lanchonete.salutem.pedido.mapper.PedidoMapper;
import com.lanchonete.salutem.pedido.model.dto.PedidoRequest;
import com.lanchonete.salutem.pedido.model.dto.PedidoResponse;
import com.lanchonete.salutem.pedido.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;
    private final HamburguerService hamburguerService;
    private final BebidaService bebidaService;


    public PedidoService(PedidoRepository repository, PedidoMapper mapper, HamburguerService hamburguerService, BebidaService bebidaService) {
        this.repository = repository;
        this.mapper = mapper;
        this.hamburguerService = hamburguerService;
        this.bebidaService = bebidaService;
    }

    public PedidoResponse getById(Long id) {
        PedidoEntity pedido = repository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido de id: " + id + " não encontrado"));
        return mapper.toResponse(pedido);
    }

    public List<PedidoResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

//    public PedidoResponse save(PedidoRequest request){
//
//        if (request.idBebidas().isEmpty() && request.idHamburgueres().isEmpty()) {
//            throw new PedidoBebidaAndHamburguerEmptyException("Ids de hamburgueres e bebidas não podem ser nulos");
//        }
//
//        //TODO: Implementar validacao de ids para hamburgueres e bebidas
//        var hamburguer = hamburguerService.findAllById(request.idHamburgueres());
//        var bebidas = bebidaService.findAllById(request.idBebidas());
//        //TODO: Refatorar pra  um key value id: quantidade
//
//        var pedido = mapper.toEntity(request);
//        pedido.setHamburgueres(hamburguer);
//        pedido.setBebidas(bebidas);
//
//        var pedidoSave = repository.save(pedido);
//
//        return mapper.toResponse(pedidoSave);
//    }

    public void delete(Long id) {
        var pedido = repository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido de id: " + id + " não encontrado"));
        repository.delete(pedido);
    }

    @Transactional
    public PedidoResponse update(Long id, PedidoRequest request) {
        if (request.idBebidaQuantidade().isEmpty() && request.idHamburguerQuantidade().isEmpty()) {
            throw new PedidoBebidaAndHamburguerEmptyException("Ids de hamburgueres e bebidas não podem ser nulos");
        }

        var pedido = repository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido de id: " + id + " não encontrado"));

        var hamburgueres = hamburguerService.findAllEntityByIds(request.idHamburguerQuantidade().keySet());
        var bebidas = bebidaService.findAllEntityByIds(request.idBebidaQuantidade().keySet());

        if (hamburgueres.isEmpty() && bebidas.isEmpty()) {
            throw new PedidoBebidaAndHamburguerEmptyException("Nenhum hamburguer ou bebida encontrado para os ids fornecidos");
        }

        verificaIdsValidosHamburguer(request, hamburgueres);
        verificaIdsValidosBebida(request, bebidas);
        mapper.updateEntityFromRequest(pedido, request);

        pedido.getHamburgueres().clear();
        for (var hamburguer : hamburgueres) {
            var quantidade = request.idHamburguerQuantidade().get(hamburguer.getId());
            var embeddedId = new PedidoHamburguerEmbedded(pedido.getId(), hamburguer.getId());
            var pedidoHamburguer = PedidoHamburguerEntity.builder()
                    .id(embeddedId)
                    .hamburguer(hamburguer)
                    .quantidade(quantidade)
                    .precoVenda(hamburguer.getValor())
                    .build();
            pedido.getHamburgueres().add(pedidoHamburguer);
        }

        pedido.getBebidas().clear();
        for (var bebida : bebidas) {
            var quantidade = request.idBebidaQuantidade().get(bebida.getId());
            var embeddedId = new PedidoBebidaEmbedded(pedido.getId(), bebida.getId());
            var pedidoBebida = PedidoBebidaEntity.builder()
                    .id(embeddedId)
                    .pedido(pedido)
                    .bebida(bebida)
                    .quantidade(quantidade)
                    .precoVenda(bebida.getPrecoUnitario())
                    .build();
            pedido.getBebidas().add(pedidoBebida);
        }

       // pedido.setValorTotal(calcularValorTotal(pedido));
    pedido.setValorTotal(calcularValorTotalPedido(pedido));
        repository.save(pedido);
        return mapper.toResponse(pedido);
    }

    @Transactional
    public PedidoResponse save(PedidoRequest request) {

        if (request.idBebidaQuantidade().isEmpty() && request.idHamburguerQuantidade().isEmpty()) {
            throw new PedidoBebidaAndHamburguerEmptyException("Ids de hamburgueres e bebidas não podem ser nulos");
        }

        var hamburgueres = hamburguerService.findAllEntityByIds(request.idHamburguerQuantidade().keySet());
        var bebidas = bebidaService.findAllEntityByIds(request.idBebidaQuantidade().keySet());

        if (hamburgueres.isEmpty() && bebidas.isEmpty()) {
            throw new PedidoItemIdsNotFoundException("Nenhum hamburguer ou bebida encontrado para os ids fornecidos");
        }

        verificaIdsValidosHamburguer(request, hamburgueres);
        verificaIdsValidosBebida(request, bebidas);


        var pedidoSalvo = repository.save(mapper.toEntity(request));

        for (var hamburguer : hamburgueres) {
            var quantidade = request.idHamburguerQuantidade().get(hamburguer.getId());
            var embeddedId = new PedidoHamburguerEmbedded(pedidoSalvo.getId(), hamburguer.getId());
            var pedidoHamburguer = PedidoHamburguerEntity.builder()
                    .id(embeddedId)
                    .pedido(pedidoSalvo)
                    .quantidade(quantidade)
                    .hamburguer(hamburguer)
                    .precoVenda(hamburguer.getValor())
                    .build();
            pedidoSalvo.getHamburgueres().add(pedidoHamburguer);
        }

        for (var bebida : bebidas) {
            var quantidade = request.idBebidaQuantidade().get(bebida.getId());
            var embeddedId = new PedidoBebidaEmbedded(pedidoSalvo.getId(), bebida.getId());
            var pedidoBebida = PedidoBebidaEntity.builder()
                    .id(embeddedId)
                    .pedido(pedidoSalvo)
                    .bebida(bebida)
                    .quantidade(quantidade)
                    .precoVenda(bebida.getPrecoUnitario())
                    .build();
            pedidoSalvo.getBebidas().add(pedidoBebida);
        }

        //pedidoSalvo.setValorTotal(calcularValorTotal(pedidoSalvo));
        pedidoSalvo.setValorTotal(calcularValorTotalPedido(pedidoSalvo));
        repository.save(pedidoSalvo);
        return mapper.toResponse(pedidoSalvo);
    }

    private static void verificaIdsValidosHamburguer(PedidoRequest request, List<HamburguerEntity> hamburgueres) {
        var idsHamburgueresEncontrados = hamburgueres
                .stream()
                .map(HamburguerEntity::getId)
                .toList();

        var idsHamburgueresInvalido = request
                .idHamburguerQuantidade()
                .keySet()
                .stream()
                .filter(id -> !idsHamburgueresEncontrados.contains(id))
                .toList();

        if (!idsHamburgueresInvalido.isEmpty()) {
            throw new PedidoItemIdsNotFoundException("Ids de hamburgueres não encontrados: " + idsHamburgueresInvalido);
        }
    }

    private static void verificaIdsValidosBebida(PedidoRequest request, List<BebidaEntity> bebidas) {
        var idsBebidasEncontrados = bebidas
                .stream()
                .map(BebidaEntity::getId)
                .toList();

        var idsBebidasInvalido = request
                .idBebidaQuantidade()
                .keySet()
                .stream()
                .filter(id -> !idsBebidasEncontrados.contains(id))
                .toList();

        if (!idsBebidasInvalido.isEmpty()) {
            throw new PedidoItemIdsNotFoundException("Ids de bebidas não encontrados: " + idsBebidasInvalido);
        }
    }

    private static BigDecimal calcularValorTotalPedido(PedidoEntity entity) {
        BigDecimal totalHamburguer = entity.getHamburgueres()
                .stream()
                .map(hamburguer -> hamburguer
                        .getPrecoVenda()
                        .multiply(BigDecimal.valueOf(hamburguer.getQuantidade()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalBebida = entity.getBebidas()
                .stream()
                .map(bebida -> bebida
                        .getPrecoVenda()
                        .multiply(BigDecimal.valueOf(bebida.getQuantidade()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalHamburguer.add(totalBebida);
    }
}