package com.lanchonete.salutem.pedido.mapper;

import com.lanchonete.salutem.pedido.model.dto.ItemResponse;
import com.lanchonete.salutem.pedido.model.entity.PedidoBebidaEntity;
import com.lanchonete.salutem.pedido.model.entity.PedidoHamburguerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "id", source = "hamburguer.id")
    @Mapping(target = "nome", source = "hamburguer.descricao")
    ItemResponse toResponse(PedidoHamburguerEntity entity);

    @Mapping(target = "id", source = "bebida.id")
    @Mapping(target = "nome", source = "bebida.descricao")
    ItemResponse toResponse(PedidoBebidaEntity entity);
}
