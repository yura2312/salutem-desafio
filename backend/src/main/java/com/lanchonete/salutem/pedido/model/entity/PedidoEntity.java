package com.lanchonete.salutem.pedido.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
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

    /*@ManyToMany
    @JoinTable(
            name = "pedido_hamburguer",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_hamburguer")
    )*/
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "pedido",
            orphanRemoval = true
    )
    private Set<PedidoHamburguerEntity> hamburgueres = new HashSet<>();

    /*@ManyToMany
    @JoinTable(
            name = "pedido_bebida",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_bebida")
    )*/
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "pedido",
            orphanRemoval = true
    )
    private Set<PedidoBebidaEntity> bebidas = new HashSet<>();

    private String observacoes;


    @PrePersist
    private void prePersist(){
        this.data = Instant.now();
        if (this.observacoes == null) {
            this.observacoes = "N/A";
        }
    }
}
