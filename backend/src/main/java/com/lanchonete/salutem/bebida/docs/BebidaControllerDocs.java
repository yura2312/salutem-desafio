package com.lanchonete.salutem.bebida.docs;

import com.lanchonete.salutem.bebida.model.dto.BebidaRequest;
import com.lanchonete.salutem.bebida.model.dto.BebidaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Tag(name = "Bebidas", description = "CRUD endpoints para bebidas")
public interface BebidaControllerDocs {

    @Operation(summary = "Busca bebidas por descrição caseinsensitive", description = "Retorna uma lista de bebida com a descrição informada")
    @ApiResponse(responseCode = "200", description = "Lista de bebidas com descrição parcial")
    ResponseEntity<List<BebidaResponse>> findByDescricao(@RequestParam String descricao);

    @Operation(summary = "Busca bebida por id")
    @ApiResponse(responseCode = "200", description = "Bebida encontrada com sucesso")
    ResponseEntity<BebidaResponse> findById(@PathVariable Long id);

    @Operation(summary = "Busca todas as bebidas")
    @ApiResponse(responseCode = "200", description = "Bebidas encontradas com sucesso")
    ResponseEntity<List<BebidaResponse>> findAll();

    @Operation(summary = "Salva uma bebida")
    ResponseEntity<BebidaResponse> save(@Valid @RequestBody BebidaRequest request);

    @Operation(summary = "Atualiza uma bebida com id valido")
    ResponseEntity<BebidaResponse> update(@PathVariable Long id, @Valid @RequestBody BebidaRequest request);

    @Operation(summary = "Deleta uma bebida")
    @ApiResponse(responseCode = "204", description = "Bebida deletada com sucesso")
    ResponseEntity<Void> delete(@PathVariable long id);
}
