package com.lanchonete.salutem.hamburguer.docs;

import com.lanchonete.salutem.hamburguer.model.dto.HamburguerRequest;
import com.lanchonete.salutem.hamburguer.model.dto.HamburguerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Hambúrgueres", description = "CRUD endpoints para hambúrgueres")
public interface HamburguerControllerDocs {

    @Operation(summary = "Busca hambúrguer por descrição caseinsensitive", description = "Retorna hambúrgueres cuja descrição contenha o texto informado")
    @ApiResponse(responseCode = "200", description = "Lista de hambúrgueres encontrada com sucesso")
    ResponseEntity<List<HamburguerResponse>> findByDescricao(@RequestParam String descricao);

    @Operation(summary = "Busca todos os hamburgueres")
    @ApiResponse(responseCode = "200", description = "Hambúrgueres encontrados com sucesso")
    ResponseEntity<List<HamburguerResponse>> getAll();

    @Operation(summary = "Busca hamburguer por id")
    @ApiResponse(responseCode = "200", description = "Hambúrguer encontrado com sucesso")
    ResponseEntity<HamburguerResponse> getById(@PathVariable Long id);

    @Operation(summary = "Salva um hamburguer")
    @ApiResponse(responseCode = "200", description = "Hambúrguer salvo com sucesso")
    ResponseEntity<HamburguerResponse> save(@Valid @RequestBody HamburguerRequest request);

    @Operation(summary = "Atualiza um hamburguer com id valido")
    @ApiResponse(responseCode = "200", description = "Hambúrguer atualizado com sucesso")
    ResponseEntity<HamburguerResponse> update(@PathVariable Long id, @Valid @RequestBody HamburguerRequest request);

    @Operation(summary = "Deleta um hamburguer")
    @ApiResponse(responseCode = "204", description = "Hamburguer deletado com sucesso")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
