package com.example.avaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetornoAvaliacaoCliente {

    private List<CartaoAprovado> cartoes;
}
