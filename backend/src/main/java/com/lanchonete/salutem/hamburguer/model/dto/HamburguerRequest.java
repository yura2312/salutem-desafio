package com.lanchonete.salutem.hamburguer.model.dto;

import com.lanchonete.salutem.ingredientes.model.dto.IngredienteResponse;

import java.math.BigDecimal;
import java.util.List;

public record HamburguerRequest (
        String descricao,
        BigDecimal valor,
        List<Long> idIngredientes
){};
