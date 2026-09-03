package com.lanchonete.salutem.ingredientes;

import com.lanchonete.salutem.ingredientes.model.IngredienteEntity;
import com.lanchonete.salutem.ingredientes.model.dto.IngredienteRequest;
import com.lanchonete.salutem.ingredientes.model.dto.IngredienteResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredienteService {

    private final IngredienteRepository repository;
    private final IngredienteMapper mapper;

    public IngredienteService(IngredienteRepository repository, IngredienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public IngredienteResponse findById(Long id) {
        var ingrediente = repository.findById(id)
                .orElseThrow(() -> new IngredienteNotFoundException("Ingrediente de id: " + id + " não encontrado"));
        return mapper.toResponse(ingrediente);
    }


    public List<IngredienteResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public IngredienteResponse save(IngredienteRequest request) {
        var ingredienteSave = mapper.toEntity(request);
        repository.save(ingredienteSave);
        return mapper.toResponse(ingredienteSave);
    }

    public List<IngredienteResponse> findByDescricao(String descricao) {
        var ingredientes = repository.findByDescricao(descricao);
        return ingredientes
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void delete(Long id) {
        var ingrediente = repository.findById(id)
                .orElseThrow(() -> new IngredienteNotFoundException("Ingrediente de id: " + id + " não encontrado"));
        repository.delete(ingrediente);
    }

    public IngredienteResponse update(Long id, IngredienteRequest request) {
        repository.findById(id)
                .orElseThrow(() -> new IngredienteNotFoundException("Ingrediente de id: " + id + " não encontrado"));
        var ingredienteEncontrado = mapper.toEntity(request);
        ingredienteEncontrado.setId(id);
        repository.save(ingredienteEncontrado);
        return mapper.toResponse(ingredienteEncontrado);
    }

    public Set<IngredienteEntity> findAllById(List<Long> ids) {
        return new HashSet<>(repository.findAllById(ids));
    }
}
