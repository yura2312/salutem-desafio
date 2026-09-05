package com.lanchonete.salutem.pedido.model.dto;

import java.math.BigDecimal;
import java.time.Instant;
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
        String observacoes,
        BigDecimal valorTotal
) {
}
