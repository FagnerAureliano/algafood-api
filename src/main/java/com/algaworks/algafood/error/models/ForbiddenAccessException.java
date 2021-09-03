package com.algaworks.algafood.error.models;

public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException(String msg) {
        super(msg);
    }
}
