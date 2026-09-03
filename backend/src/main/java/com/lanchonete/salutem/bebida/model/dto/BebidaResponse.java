package com.lanchonete.salutem.bebida.model.dto;

import java.math.BigDecimal;

public record BebidaResponse(
        Long id,
        String descricao,
        BigDecimal precoUnitario,
        Boolean contemAcucar) {
}
