package com.lanchonete.salutem.ingredientes;

import com.lanchonete.salutem.ingredientes.model.dto.IngredienteRequest;
import com.lanchonete.salutem.ingredientes.model.dto.IngredienteResponse;
import com.lanchonete.salutem.ingredientes.docs.IngredienteControllerDocs;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingrediente")
public class IngredienteController implements IngredienteControllerDocs {

    private final IngredienteService service;

    public IngredienteController(IngredienteService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponse> findById(@PathVariable Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<IngredienteResponse>> findByDescricao(@RequestParam String descricao) {
        var response = service.findByDescricao(descricao);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<IngredienteResponse>> findAll() {
        var response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<IngredienteResponse> save(@Valid @RequestBody IngredienteRequest request) {
        var response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponse> update(@PathVariable Long id, @Valid @RequestBody IngredienteRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }
}
