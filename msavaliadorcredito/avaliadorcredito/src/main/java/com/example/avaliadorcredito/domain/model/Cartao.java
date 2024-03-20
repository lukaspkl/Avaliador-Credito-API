package com.example.avaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cartao {

    private Long id;
    private String nome;
    private String bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;
}


