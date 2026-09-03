package com.lanchonete.salutem.ingredientes.model.dto;

import java.math.BigDecimal;

public record IngredienteResponse (
        Long id,
        String descricao,
        BigDecimal precoUnitario,
        Boolean adicional
){}
