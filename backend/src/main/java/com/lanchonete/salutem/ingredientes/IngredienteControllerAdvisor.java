package com.lanchonete.salutem.ingredientes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IngredienteControllerAdvisor {

    @ExceptionHandler(IngredienteNotFoundException.class)
    public ResponseEntity<ProblemDetail> ingredienteNotFoundExceptionHandler(IngredienteNotFoundException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Ingrediente não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
}
