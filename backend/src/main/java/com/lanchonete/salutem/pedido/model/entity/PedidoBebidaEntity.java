package com.lanchonete.salutem.pedido.model.entity;

import com.lanchonete.salutem.bebida.model.BebidaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}
