package com.lanchonete.salutem.pedido.controller;

import com.lanchonete.salutem.pedido.exception.PedidoBebidaAndHamburguerEmptyException;
import com.lanchonete.salutem.pedido.exception.PedidoItemIdsNotFoundException;
import com.lanchonete.salutem.pedido.exception.PedidoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PedidoControllerAdvice {

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ProblemDetail> pedidoNotFoundExceptionHandler(PedidoNotFoundException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Pedido não encontrado");
        return ResponseEntity.status(404).body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> requestValidationExceptionHandler(MethodArgumentNotValidException ex) {
        String detail = ex.getFieldError().getDefaultMessage();
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        problem.setTitle("Request inválido");
        return ResponseEntity.status(400).body(problem);
    }

    @ExceptionHandler(PedidoBebidaAndHamburguerEmptyException.class)
    public ResponseEntity<ProblemDetail> pedidoBebidaAndHamburguerEmptyExceptionHandler(PedidoBebidaAndHamburguerEmptyException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Bebida e Hamburguer nulos");
        return ResponseEntity.status(400).body(problem);
    }

    @ExceptionHandler(PedidoItemIdsNotFoundException.class)
    public ResponseEntity<ProblemDetail> pedidoItemIdsNotFoundExceptionHandler(PedidoItemIdsNotFoundException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Ids de itens não encontrados");
        return ResponseEntity.status(400).body(problem);
    }


}
