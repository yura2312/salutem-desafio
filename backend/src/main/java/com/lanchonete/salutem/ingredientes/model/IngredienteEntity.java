package com.lanchonete.salutem.ingredientes.model;

import com.lanchonete.salutem.hamburguer.model.HamburguerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ingrediente")
public class IngredienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private BigDecimal precoUnitario;

    private Boolean adicional;

    @ManyToMany(mappedBy = "ingredientes")
    private Set<HamburguerEntity> hamburgueres = new HashSet<>();
}
