package com.example.avaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoCliente {

    private String nome;
    private String bandeira;
    private Integer limiteLiberado;
}
