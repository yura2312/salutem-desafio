package com.lanchonete.salutem.bebida;

public class BebidaNotFoundException extends RuntimeException {
    public BebidaNotFoundException(String message) {
        super(message);
    }
}
