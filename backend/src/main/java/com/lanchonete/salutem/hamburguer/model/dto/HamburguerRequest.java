package com.lanchonete.salutem.hamburguer.model.dto;

import com.lanchonete.salutem.ingredientes.model.dto.IngredienteResponse;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;


public record HamburguerRequest (

        @NotNull(message = "Descrição não pode ser nula")
        String descricao,
        @NotNull(message = "Valor não pode ser nulo")
        BigDecimal valor,
        @NotNull(message = "Lista de ingredientes não pode ser nula")
        List<Long> idIngredientes
){};
