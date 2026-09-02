package com.lanchonete.salutem.bebida.model.dto;

import java.math.BigDecimal;

public record BebidaResponse(
        String descricao,
        BigDecimal precoUnitario,
        Boolean contemAcucar) {
}
