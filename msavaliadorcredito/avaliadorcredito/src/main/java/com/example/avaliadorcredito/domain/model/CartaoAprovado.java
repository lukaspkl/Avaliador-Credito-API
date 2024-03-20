package com.example.avaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor

public class CartaoAprovado {

    private String cartao;
    private String bandeira;
    private BigDecimal limiteAprovado;
}
