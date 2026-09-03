package com.lanchonete.salutem.hamburguer;

import com.lanchonete.salutem.hamburguer.model.dto.HamburguerRequest;
import com.lanchonete.salutem.hamburguer.model.dto.HamburguerResponse;
import com.lanchonete.salutem.ingredientes.IngredienteRepository;
import com.lanchonete.salutem.ingredientes.IngredienteService;
import com.lanchonete.salutem.ingredientes.model.IngredienteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HamburguerService {

    private final HamburguerRepository repository;
    private final HamburguerMapper mapper;
    private final IngredienteService ingredienteService;

    public HamburguerService(HamburguerRepository repository, HamburguerMapper mapper, IngredienteService ingredienteService) {
        this.repository = repository;
        this.mapper = mapper;
        this.ingredienteService = ingredienteService;
    }

    public HamburguerResponse findById(Long id){
        var hamburguer = repository.findById(id)
                .orElseThrow(() -> new HamburguerNotFoundException("Hamburguer de id: " + id + " não encontrado"));
        return mapper.toResponse(hamburguer);
    }

    public List<HamburguerResponse> findByDescricao(String descricao){
        var hamburguers = repository.findByDescricao(descricao);
        return hamburguers
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public HamburguerResponse save(HamburguerRequest request){
        var hamburguer = mapper.toEntity(request);
        /*var listIngrediente = ingredienteRepository.findAllById(request.idIngredientes());

        Set<IngredienteEntity> setIngrediente = new HashSet<>(listIngrediente);*/
        var listI = ingredienteService.findAllById(request.idIngredientes());
        hamburguer.setIngredientes(listI);

        var save = repository.save(hamburguer);
        return mapper.toResponse(save);
    }

    public List<HamburguerResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void delete(Long id) {
        var hamburguer = repository.findById(id)
                .orElseThrow(() -> new HamburguerNotFoundException("Hamburguer de id: " + id + " não encontrado"));
        repository.delete(hamburguer);
    }


    public HamburguerResponse update(Long id, HamburguerRequest request) {
        var hamburguer = repository.findById(id)
                .orElseThrow(() -> new HamburguerNotFoundException("Hamburguer de id: " + id + " não encontrado"));
        var updatedHamburguer = mapper.toEntity(request);
        updatedHamburguer.setId(id);

        Set<IngredienteEntity> setIngrediente = ingredienteService.findAllById(request.idIngredientes());
        updatedHamburguer.setIngredientes(setIngrediente);

        var saved = repository.save(updatedHamburguer);
        return mapper.toResponse(saved);
    }
}
