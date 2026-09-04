package com.lanchonete.salutem.pedido.exception;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(String message) {
        super(message);
    }
}
