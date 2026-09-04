package com.lanchonete.salutem.pedido.model.dto;

import com.lanchonete.salutem.bebida.model.dto.BebidaResponse;
import com.lanchonete.salutem.hamburguer.model.dto.HamburguerResponse;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record PedidoResponse(
        Long id,
        Instant data,
        String descricao,
        String clienteNome,
        String clienteEndereco,
        String clienteTelefone,
        Set<ItemResponse> hamburgueres,
        Set<ItemResponse> bebidas,
        String observacoes
) {
}
