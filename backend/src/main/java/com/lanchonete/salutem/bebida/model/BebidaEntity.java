package com.lanchonete.salutem.bebida.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "bebida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
