package com.example.avaliadorcredito.application.ex;

import lombok.Getter;

public class ErroComunicacaoMicroservicesException extends Exception{

    @Getter
    private final Integer status;

    public ErroComunicacaoMicroservicesException(String msg,Integer status) {
        super(msg);
        this.status = status;
    }
}
