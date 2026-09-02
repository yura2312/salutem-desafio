package com.lanchonete.salutem.bebida.model.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BebidaRequest(
        @NotNull(message = "Descrição não pode ser nula")
        String descricao,
        @NotNull(message = "Preço unitário não pode ser nulo")
        BigDecimal precoUnitario,
        @NotNull(message = "Contém açúcar não pode ser nulo")
        Boolean contemAcucar
) {
}
