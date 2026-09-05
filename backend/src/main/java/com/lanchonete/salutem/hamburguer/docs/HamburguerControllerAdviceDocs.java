package com.lanchonete.salutem.hamburguer.docs;

import com.lanchonete.salutem.hamburguer.exception.HamburguerIngredienteEmptyException;
import com.lanchonete.salutem.hamburguer.exception.HamburguerNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface HamburguerControllerAdviceDocs {

    @ExceptionHandler(HamburguerNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Hambúrguer não encontrado",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            name = "Hambúrguer não encontrado",
                            value = """
                                    {
                                      "detail": "Hambúrguer de id: 10 não encontrado",
                                      "instance": "/api/hamburguers/10",
                                      "status": 404,
                                      "title": "Hambúrguer não encontrado"
                                    }
                                    """
                    )))
    ResponseEntity<ProblemDetail> hamburguerNotFoundExceptionHandler(HamburguerNotFoundException ex);


    @ApiResponse(responseCode = "400", description = "Lista de ingredientes vazia",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            name = "Lista de ingredientes vazia",
                            value = """
                                    {
                                      "detail": "Lista de ingredientes não pode ser vazia",
                                      "instance": "/api/hamburguers",
                                      "status": 400,
                                      "title": "Lista de ingredientes vazia"
                                    }
                                    """
                    )))
    ResponseEntity<ProblemDetail> hamburguerIngredienteEmptyExceptionHandler(HamburguerIngredienteEmptyException ex);
}