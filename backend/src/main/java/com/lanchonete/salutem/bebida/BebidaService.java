package com.lanchonete.salutem.bebida;

import com.lanchonete.salutem.bebida.model.dto.BebidaResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BebidaService {

    private final BebidaRepository repository;
    private final BebidaMapper mapper;
    public BebidaService(BebidaRepository repository, BebidaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public BebidaResponse findById(Long id){
        var bebida = repository.findById(id)
                .orElseThrow(() -> new BebidaNotFoundException("Bebida de id: " + id + " não encontrada"));
        return mapper.toBebidaResponse(bebida);
    }

}
