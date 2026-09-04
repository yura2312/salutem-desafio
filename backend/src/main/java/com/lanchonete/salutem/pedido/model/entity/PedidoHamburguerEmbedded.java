package com.lanchonete.salutem.pedido.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PedidoHamburguerEmbedded implements Serializable {

    private Long idPedido;

    private Long idHamburguer;
}
