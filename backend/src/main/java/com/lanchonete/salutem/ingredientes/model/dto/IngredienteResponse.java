package com.lanchonete.salutem.ingredientes.model.dto;

public record IngredienteResponse (
        String descricao,
        Double precoUnitario,
        Boolean adicional
){}
