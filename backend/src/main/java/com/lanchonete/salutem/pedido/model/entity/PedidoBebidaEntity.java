package com.lanchonete.salutem.pedido.model.entity;

import com.lanchonete.salutem.bebida.model.BebidaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pedido_bebida")
public class PedidoBebidaEntity {

    @EmbeddedId
    private PedidoBebidaEmbedded id;

    @ManyToOne
    @MapsId("idPedido")
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedido;

    @ManyToOne
    @MapsId("idBebida")
    @JoinColumn(name = "id_bebida")
    private BebidaEntity bebida;

    private Integer quantidade;

    private BigDecimal precoVenda;

}
