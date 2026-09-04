package com.lanchonete.salutem.pedido;

import com.lanchonete.salutem.pedido.model.PedidoEntity;
import com.lanchonete.salutem.pedido.model.dto.PedidoRequest;
import com.lanchonete.salutem.pedido.model.dto.PedidoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoEntity toEntity(PedidoRequest request);

    PedidoResponse toResponse(PedidoEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "hamburgueres", ignore = true)
    @Mapping(target = "bebidas", ignore = true)
    void updateEntityFromRequest(@MappingTarget PedidoEntity entity, PedidoRequest request);

}

