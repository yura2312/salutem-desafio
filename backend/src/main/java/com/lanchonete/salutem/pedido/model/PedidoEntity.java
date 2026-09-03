package com.lanchonete.salutem.pedido.model;

import com.lanchonete.salutem.bebida.model.BebidaEntity;
import com.lanchonete.salutem.hamburguer.model.HamburguerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedido")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant data;

    private String descricao;

    private String clienteNome;

    private String clienteEndereco;

    private String clienteTelefone;

    @ManyToMany
    @JoinTable(
            name = "pedido_hamburguer",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_hamburguer")
    )
    private Set<HamburguerEntity> hamburgueres = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "pedido_bebida",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_bebida")
    )
    private Set<BebidaEntity> bebidas = new HashSet<>();

}
