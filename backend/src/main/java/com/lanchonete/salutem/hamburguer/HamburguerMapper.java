package com.lanchonete.salutem.hamburguer;

import com.lanchonete.salutem.hamburguer.model.HamburguerEntity;
import com.lanchonete.salutem.hamburguer.model.dto.HamburguerRequest;
import com.lanchonete.salutem.hamburguer.model.dto.HamburguerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HamburguerMapper {

    HamburguerResponse toResponse(HamburguerEntity entity);

    @Mapping(target = "ingredientes", ignore = true)
    HamburguerEntity toEntity(HamburguerRequest request);
}
