package com.lanchonete.salutem.bebida.model.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BebidaResponse(
        String descricao,
        BigDecimal precoUnitario,
        Boolean contemAcucar) {
}
