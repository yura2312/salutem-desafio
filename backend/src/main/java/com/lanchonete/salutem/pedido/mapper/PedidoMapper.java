package com.lanchonete.salutem.pedido.mapper;

import com.lanchonete.salutem.pedido.model.entity.PedidoEntity;
import com.lanchonete.salutem.pedido.model.dto.PedidoRequest;
import com.lanchonete.salutem.pedido.model.dto.PedidoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface PedidoMapper {

    PedidoResponse toResponse(PedidoEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "hamburgueres", ignore = true)
    @Mapping(target = "bebidas", ignore = true)
    void updateEntityFromRequest(@MappingTarget PedidoEntity entity, PedidoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "hamburgueres", ignore = true)
    @Mapping(target = "bebidas", ignore = true)
    PedidoEntity toEntity(PedidoRequest request);

}
