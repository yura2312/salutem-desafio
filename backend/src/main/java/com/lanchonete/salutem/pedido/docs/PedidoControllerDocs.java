package com.lanchonete.salutem.pedido.docs;

import com.lanchonete.salutem.pedido.model.dto.PedidoRequest;
import com.lanchonete.salutem.pedido.model.dto.PedidoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public interface PedidoControllerDocs {

    @Operation(summary = "Busca pedido por id")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso")
    PedidoResponse getById(@PathVariable Long id);

    @Operation(summary = "Busca todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso")
    List<PedidoResponse> getAll();

    @Operation(summary = "Cria um pedido")
    @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso")
    PedidoResponse save(@Valid @RequestBody PedidoRequest request);

    @Operation(summary = "Atualiza um pedido com id válido")
    @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso")
    PedidoResponse update(@PathVariable Long id, @Valid @RequestBody PedidoRequest request);

    @Operation(summary = "Deleta um pedido")
    @ApiResponse(responseCode = "200", description = "Pedido deletado com sucesso")
    void delete(@PathVariable Long id);
}
