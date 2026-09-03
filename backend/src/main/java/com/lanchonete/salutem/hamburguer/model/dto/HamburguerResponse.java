package com.lanchonete.salutem.hamburguer.model.dto;

import com.lanchonete.salutem.ingredientes.model.dto.IngredienteResponse;

import java.math.BigDecimal;
import java.util.Set;

public record HamburguerResponse(
        String id,
        String descricao,
        BigDecimal valor,
        Set<IngredienteResponse> ingredientes) {
}
