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
        @Length(min = 10, message = "Descrição deve ter pelo menos 10 caracteres")
        String descricao,

        @NotNull(message = "Nome do cliente não pode ser nulo")
        @Length(min = 10, message = "Nome do cliente deve ter pelo menos 10 caracteres")
        String clienteNome,

        @NotNull(message = "Endereço do cliente não pode ser nulo")
        @Length(min = 10, message = "Endereço do cliente deve ter pelo menos 10 caracteres")
        String clienteEndereco,

        @NotNull(message = "Telefone do cliente não pode ser nulo")
        @Length(min = 10, max = 11, message = "Telefone do cliente deve ter entre 10 e 11 dígitos")
        String clienteTelefone,

        @NotNull(message = "A lista de ids de hamburgueres não pode ser nula")
        List<@Positive(message = "A lista de ids de hamburgueres não pode conter valores negativos")
                Long> idHamburgueres,
        List<@Positive(message = "A lista de ids de bebidas não pode conter valores negativos")
                Long> idBebidas,
        @Length(max = 100, message = "As observações não podem ter mais de 100 caracteres")
        String observacoes
) {
}
