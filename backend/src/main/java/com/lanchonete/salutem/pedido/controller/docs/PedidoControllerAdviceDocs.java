package com.lanchonete.salutem.pedido.controller.docs;

import com.lanchonete.salutem.pedido.exception.PedidoBebidaAndHamburguerEmptyException;
import com.lanchonete.salutem.pedido.exception.PedidoItemIdsNotFoundException;
import com.lanchonete.salutem.pedido.exception.PedidoNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface PedidoControllerAdviceDocs {

    @ExceptionHandler(PedidoNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            name = "Pedido não encontrado",
                            value = """
                                    {
                                      "detail": "Pedido de id: 1 não encontrado",
                                      "instance": "/api/pedidos/1",
                                      "status": 404,
                                      "title": "Pedido não encontrado"
                                    }
                                    """
                    )))
    ResponseEntity<ProblemDetail> pedidoNotFoundExceptionHandler(PedidoNotFoundException ex);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "Request inválido",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<ProblemDetail> requestValidationExceptionHandler(MethodArgumentNotValidException ex);

    @ExceptionHandler(PedidoBebidaAndHamburguerEmptyException.class)
    @ApiResponse(responseCode = "400", description = "Pedido sem bebida e sem hambúrguer",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<ProblemDetail> pedidoBebidaAndHamburguerEmptyExceptionHandler(PedidoBebidaAndHamburguerEmptyException ex);

    @ExceptionHandler(PedidoItemIdsNotFoundException.class)
    @ApiResponse(responseCode = "400", description = "IDs de itens não encontrados",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
    ResponseEntity<ProblemDetail> pedidoItemIdsNotFoundExceptionHandler(PedidoItemIdsNotFoundException ex);
}
