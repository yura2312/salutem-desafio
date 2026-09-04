package com.lanchonete.salutem.hamburguer;

import com.lanchonete.salutem.hamburguer.docs.HamburguerControllerAdviceDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HamburguerControllerAdvisor implements HamburguerControllerAdviceDocs {

    @ExceptionHandler(HamburguerNotFoundException.class)
    @Override
    public ResponseEntity<ProblemDetail> hamburguerNotFoundExceptionHandler(HamburguerNotFoundException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Hambúrguer não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
}
