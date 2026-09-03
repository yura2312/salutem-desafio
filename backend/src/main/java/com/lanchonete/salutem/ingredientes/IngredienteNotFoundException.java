package com.lanchonete.salutem.ingredientes;

public class IngredienteNotFoundException extends RuntimeException {
    public IngredienteNotFoundException(String message) {
        super(message);
    }
}
