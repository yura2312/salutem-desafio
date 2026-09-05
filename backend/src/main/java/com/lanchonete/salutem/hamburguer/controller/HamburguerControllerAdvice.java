package com.lanchonete.salutem.hamburguer.controller;

import com.lanchonete.salutem.hamburguer.exception.HamburguerIngredienteEmptyException;
import com.lanchonete.salutem.hamburguer.exception.HamburguerNotFoundException;
import com.lanchonete.salutem.hamburguer.docs.HamburguerControllerAdviceDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HamburguerControllerAdvice implements HamburguerControllerAdviceDocs {

    @ExceptionHandler(HamburguerNotFoundException.class)
    @Override
    public ResponseEntity<ProblemDetail> hamburguerNotFoundExceptionHandler(HamburguerNotFoundException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Hambúrguer não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(HamburguerIngredienteEmptyException.class)
    public ResponseEntity<ProblemDetail> hamburguerIngredienteEmptyExceptionHandler(HamburguerIngredienteEmptyException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Lista de ingredientes vazia");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
}
