package com.lanchonete.salutem.pedido.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public record PedidoRequest(
        @NotNull(message = "Descrição não pode ser nula")
        String descricao,
        @NotNull(message = "Nome do cliente não pode ser nulo")
        String clienteNome,
        @NotNull(message = "Endereço do cliente não pode ser nulo")
        String clienteEndereco,
        @NotNull(message = "Telefone do cliente não pode ser nulo")
        @Length(min = 10, max = 11, message = "Telefone do cliente deve ter entre 10 e 11 dígitos")
        String clienteTelefone,
        List<@Positive(message = "A lista de ids de hamburgueres não pode conter valores negativos")
                Long> idHamburgueres,
        List<@Positive(message = "A lista de ids de bebidas não pode conter valores negativos")
                Long> idBebidas,

        String observacoes
) {
}
