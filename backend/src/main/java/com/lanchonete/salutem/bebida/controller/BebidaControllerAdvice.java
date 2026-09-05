package com.lanchonete.salutem.bebida.controller;

import com.lanchonete.salutem.bebida.BebidaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BebidaControllerAdvice implements com.lanchonete.salutem.bebida.docs.BebidaControllerAdviceDocs {

    @ExceptionHandler(BebidaNotFoundException.class)
    public ResponseEntity<ProblemDetail> bebidaNotFoundExceptionHandler(BebidaNotFoundException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Bebida não encontrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
}
