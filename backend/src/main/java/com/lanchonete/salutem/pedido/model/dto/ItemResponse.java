package com.lanchonete.salutem.pedido.model.dto;

import java.math.BigDecimal;

public record ItemResponse(
        Long id,
        String nome,
        Integer quantidade,
        BigDecimal precoVenda
) {
}
