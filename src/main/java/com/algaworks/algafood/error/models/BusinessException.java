package com.algaworks.algafood.error.models;

import java.io.Serializable;
import java.util.Map;

public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Map<String, Serializable> error;

    public BusinessException(Map<String, Serializable> error) {
        super("Not Found Exception");
        this.error = error;
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public Map<String, Serializable> getError() {
        return error;
    }

    public void setError(Map<String, Serializable> error) {
        this.error = error;
    }
}
