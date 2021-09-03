package com.algaworks.algafood.error.models;

public class IntegrationException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IntegrationException(String msg) {
        super(msg);
    }
}
