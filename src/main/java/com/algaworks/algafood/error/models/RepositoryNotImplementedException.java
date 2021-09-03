package com.algaworks.algafood.error.models;

public class RepositoryNotImplementedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RepositoryNotImplementedException(String msg) {
        super(msg);
    }
}
