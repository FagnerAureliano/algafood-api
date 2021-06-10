package com.algaworks.algafood.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Restaurante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "TAXA_FRETE")
    private BigDecimal taxaFrete;
    @ManyToOne
    private Cozinha cozinha;

}
