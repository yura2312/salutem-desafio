package com.lanchonete.salutem.ingredientes.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record IngredienteRequest (
        @NotNull(message = "Descrição não pode ser nula")
        String descricao,

        @NotNull(message = "Preço unitário não pode ser nulo")
        @Positive(message = "Preço unitário deve ser positivo")
        BigDecimal precoUnitario,

        @NotNull(message = "Adicional não pode ser nulo")
        Boolean adicional
){}