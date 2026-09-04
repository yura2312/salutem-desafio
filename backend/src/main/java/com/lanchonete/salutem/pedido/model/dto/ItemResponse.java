package com.lanchonete.salutem.pedido.model.dto;

public record ItemResponse(
        Long id,
        String nome,
        Integer quantidade
) {
}
