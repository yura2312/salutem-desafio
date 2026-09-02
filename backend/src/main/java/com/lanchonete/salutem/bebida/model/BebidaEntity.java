package com.lanchonete.salutem.bebida.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "bebida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BebidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String descricao;

    @NotNull
    private BigDecimal precoUnitario;

    @NotNull
    private Boolean contemAcucar;
}
