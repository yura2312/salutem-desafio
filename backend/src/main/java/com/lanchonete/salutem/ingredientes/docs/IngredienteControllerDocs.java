package com.lanchonete.salutem.ingredientes.docs;

import com.lanchonete.salutem.ingredientes.model.dto.IngredienteRequest;
import com.lanchonete.salutem.ingredientes.model.dto.IngredienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Ingredientes", description = "CRUD endpoints para ingredientes")
public interface IngredienteControllerDocs {

    @Operation(summary = "Busca ingrediente por descrição", description = "Retorna ingredientes cuja descrição contenha o texto informado")
    @ApiResponse(responseCode = "200", description = "Lista de ingredientes encontrada com sucesso")
    ResponseEntity<List<IngredienteResponse>> findByDescricao(@RequestParam String descricao);

    @Operation(summary = "Busca ingrediente por id")
    @ApiResponse(responseCode = "200", description = "Ingrediente encontrado com sucesso")
    ResponseEntity<IngredienteResponse> findById(@PathVariable Long id);

    @Operation(summary = "Busca todos os ingredientes")
    @ApiResponse(responseCode = "200", description = "Ingredientes encontrados com sucesso")
    ResponseEntity<List<IngredienteResponse>> findAll();

    @Operation(summary = "Salva um ingrediente")
    @ApiResponse(responseCode = "201", description = "Ingrediente salvo com sucesso")
    ResponseEntity<IngredienteResponse> save(@Valid @RequestBody IngredienteRequest request);

    @Operation(summary = "Atualiza um ingrediente com id válido")
    @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso")
    ResponseEntity<IngredienteResponse> update(@PathVariable Long id, @Valid @RequestBody IngredienteRequest request);

    @Operation(summary = "Deleta um ingrediente")
    @ApiResponse(responseCode = "204", description = "Ingrediente deletado com sucesso")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
