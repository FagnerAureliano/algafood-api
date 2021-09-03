package com.algaworks.algafood.error.models;

import java.io.Serializable;
import java.util.Map;

public class ExternalServerDownException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Map<String, Serializable> error;

    public ExternalServerDownException(Map<String, Serializable> error) {
        super("Server Down Exception");
        this.error = error;
    }

    public ExternalServerDownException(String msg) {
        super(msg);
    }

    public Map<String, Serializable> getError() {
        return error;
    }

    public void setError(Map<String, Serializable> error) {
        this.error = error;
    }
}
