package com.lanchonete.salutem.bebida.docs;

import com.lanchonete.salutem.bebida.BebidaNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface BebidaControllerAdviceDocs {

    @ExceptionHandler(BebidaNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Bebida não encontrada",
            content = {@Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            name = "Bebida nao encontrada",
                            value = """
                                    {
                                      "detail": "Bebida de id: 10 não encontrada",
                                      "instance": "/api/bebidas/10",
                                      "status": 404,
                                      "title": "Bebida não encontrada"
                                    }
                                    
                                    """
                    )
            )
            })
    ResponseEntity<ProblemDetail> bebidaNotFoundExceptionHandler(BebidaNotFoundException ex);
}
