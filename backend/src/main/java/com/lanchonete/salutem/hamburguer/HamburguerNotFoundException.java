package com.lanchonete.salutem.hamburguer;

public class HamburguerNotFoundException extends RuntimeException {
    public HamburguerNotFoundException(String message) {
        super(message);
    }
}
