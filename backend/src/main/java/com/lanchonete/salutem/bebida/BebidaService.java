package com.lanchonete.salutem.bebida;

import com.lanchonete.salutem.bebida.model.BebidaEntity;
import com.lanchonete.salutem.bebida.model.dto.BebidaRequest;
import com.lanchonete.salutem.bebida.model.dto.BebidaResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BebidaService {

    private final BebidaRepository repository;
    private final BebidaMapper mapper;

    public BebidaService(BebidaRepository repository, BebidaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public BebidaResponse findById(Long id) {
        var bebida = repository.findById(id)
                .orElseThrow(() -> new BebidaNotFoundException("Bebida de id: " + id + " não encontrada"));
        return mapper.toBebidaResponse(bebida);
    }

    public List<BebidaResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toBebidaResponse)
                .toList();
    }

    public BebidaResponse save(BebidaRequest request) {
        var bebidaSave = mapper.toBebidaEntity(request);
        repository.save(bebidaSave);
        return mapper.toBebidaResponse(bebidaSave);
    }

    public List<BebidaResponse> findByDescricao(String descricao) {
        var bebidas = repository.findByDescricao(descricao);
        return bebidas
                .stream()
                .map(mapper::toBebidaResponse)
                .toList();
    }

    public void delete(Long id) {
        var bebida = repository.findById(id)
                .orElseThrow(() -> new BebidaNotFoundException("Bebida de id: " + id + " não encontrada"));
        repository.delete(bebida);
    }

    public BebidaResponse update(Long id, BebidaRequest request) {
        var bebidaEncontrada = repository.findById(id)
                .orElseThrow(() -> new BebidaNotFoundException("Bebida de id: " + id + " não encontrada"));
        var bebidaSalva = mapper.toBebidaEntity(request);
        bebidaSalva.setId(bebidaEncontrada.getId());
        repository.save(bebidaSalva);
        return mapper.toBebidaResponse(bebidaSalva);
    }

    public List<BebidaEntity> findAllEntityByIds(Set<Long> longs) {
        return repository.findAllById(longs);
    }
}
