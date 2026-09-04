package com.lanchonete.salutem.pedido.model.entity;

import com.lanchonete.salutem.hamburguer.model.HamburguerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pedido_hamburguer")
public class PedidoHamburguerEntity {

    @EmbeddedId
    private PedidoHamburguerEmbedded id;

    @ManyToOne
    @MapsId("idPedido")
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedido;

    @ManyToOne
    @MapsId("idHamburguer")
    @JoinColumn(name = "id_hamburguer")
    private HamburguerEntity hamburguer;

    private BigDecimal precoVenda;

    private Integer quantidade;

}
