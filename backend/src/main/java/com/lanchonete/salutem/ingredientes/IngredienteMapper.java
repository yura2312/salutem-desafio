package com.lanchonete.salutem.ingredientes;

import com.lanchonete.salutem.ingredientes.model.IngredienteEntity;
import com.lanchonete.salutem.ingredientes.model.dto.IngredienteRequest;
import com.lanchonete.salutem.ingredientes.model.dto.IngredienteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredienteMapper {

    public IngredienteResponse toResponse(IngredienteEntity entity);

    public IngredienteEntity toEntity(IngredienteRequest request);

}
