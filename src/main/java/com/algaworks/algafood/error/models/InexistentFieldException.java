package com.algaworks.algafood.error.models;

public class InexistentFieldException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InexistentFieldException(String msg) {
        super(msg);
    }
}
