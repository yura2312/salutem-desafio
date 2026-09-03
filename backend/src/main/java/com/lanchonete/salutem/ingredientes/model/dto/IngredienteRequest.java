package com.lanchonete.salutem.ingredientes.model.dto;

public record IngredienteRequest (
        Long id,
        String descricao,
        Double precoUnitario,
        Boolean adicional
){}