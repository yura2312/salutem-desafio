package com.lanchonete.salutem.ingredientes.controller;

import com.lanchonete.salutem.ingredientes.IngredienteNotFoundException;
import com.lanchonete.salutem.ingredientes.docs.IngredienteControllerAdviceDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IngredienteControllerAdvice implements IngredienteControllerAdviceDocs {

    @ExceptionHandler(IngredienteNotFoundException.class)
    @Override
    public ResponseEntity<ProblemDetail> ingredienteNotFoundExceptionHandler(IngredienteNotFoundException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Ingrediente não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
}
