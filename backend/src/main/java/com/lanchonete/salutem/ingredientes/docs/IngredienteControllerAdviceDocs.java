package com.lanchonete.salutem.ingredientes.docs;

import com.lanchonete.salutem.ingredientes.IngredienteNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface IngredienteControllerAdviceDocs {

    @ExceptionHandler(IngredienteNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            name = "Ingrediente não encontrado",
                            value = """
                                    {
                                      "detail": "Ingrediente de id: 10 não encontrado",
                                      "instance": "/api/ingrediente/10",
                                      "status": 404,
                                      "title": "Ingrediente não encontrado"
                                    }
                                    """
                    )))
    ResponseEntity<ProblemDetail> ingredienteNotFoundExceptionHandler(IngredienteNotFoundException ex);
}
