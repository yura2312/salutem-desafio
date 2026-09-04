package com.lanchonete.salutem.pedido;

import com.lanchonete.salutem.bebida.BebidaService;
import com.lanchonete.salutem.hamburguer.HamburguerService;
import com.lanchonete.salutem.pedido.model.PedidoEntity;
import com.lanchonete.salutem.pedido.model.dto.PedidoRequest;
import com.lanchonete.salutem.pedido.model.dto.PedidoResponse;
import org.springframework.stereotype.Service;

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

    public PedidoResponse getById(Long id){
        PedidoEntity pedido = repository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido de id: " + id + " não encontrado"));
        return mapper.toResponse(pedido);
    }

    public List<PedidoResponse> getAll(){
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public PedidoResponse save(PedidoRequest request){

        if (request.idBebidas().isEmpty() && request.idHamburgueres().isEmpty()) {
            throw new PedidoBebidaAndHamburguerEmptyException("Ids de hamburgueres e bebidas não podem ser nulos");
        }

        //TODO: Implementar validacao de ids para hamburgueres e bebidas
        var hamburguer = hamburguerService.findAllById(request.idHamburgueres());
        var bebidas = bebidaService.findAllById(request.idBebidas());
        //TODO: Refatorar pra  um key value id: quantidade

        var pedido = mapper.toEntity(request);
        pedido.setHamburgueres(hamburguer);
        pedido.setBebidas(bebidas);

        var pedidoSave = repository.save(pedido);

        return mapper.toResponse(pedidoSave);
    }

    public void delete(Long id){
        var pedido = repository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido de id: " + id + " não encontrado"));
        repository.delete(pedido);
    }

    public PedidoResponse update(Long id, PedidoRequest request){

        if (request.idBebidas().isEmpty() && request.idHamburgueres().isEmpty()) {
            throw new IllegalArgumentException("Ids de hamburgueres e bebidas não podem ser nulos");
        }

        var pedido = repository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido de id: " + id + " não encontrado"));

        var hamburguer = hamburguerService.findAllById(request.idHamburgueres());
        var bebidas = bebidaService.findAllById(request.idBebidas());

        mapper.updateEntityFromRequest(pedido, request);
        pedido.setHamburgueres(hamburguer);
        pedido.setBebidas(bebidas);

        var pedidoSave = repository.save(pedido);

        return mapper.toResponse(pedidoSave);
    }


}
