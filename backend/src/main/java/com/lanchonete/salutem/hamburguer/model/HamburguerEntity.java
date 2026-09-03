package com.lanchonete.salutem.hamburguer.model;

import com.lanchonete.salutem.ingredientes.model.IngredienteEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "hamburguer")
public class HamburguerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private BigDecimal valor;

    @ManyToMany
    @JoinTable(
            name = "ingrediente_hamburguer",
            joinColumns = @JoinColumn(name = "id_hamburguer"),
            inverseJoinColumns = @JoinColumn(name = "id_ingrediente")
    )
    private Set<IngredienteEntity> ingredientes = new HashSet<>();
}
