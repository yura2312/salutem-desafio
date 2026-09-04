package com.lanchonete.salutem.pedido.model.dto;

import java.util.List;

public record PedidoRequest(
        String descricao,
        String clienteNome,
        String clienteEndereco,
        String clienteTelefone,
        List<Long> idHamburgueres,
        List<Long> idBebidas,
        String observacoes
) {
}
