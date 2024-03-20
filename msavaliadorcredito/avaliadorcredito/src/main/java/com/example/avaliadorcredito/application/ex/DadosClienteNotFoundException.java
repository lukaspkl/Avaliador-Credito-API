package com.example.avaliadorcredito.application.ex;

public class DadosClienteNotFoundException extends Exception{
    public DadosClienteNotFoundException() {
        super("Dados do Cliente nao encontrados para o CPF informado");
    }
}
