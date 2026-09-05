package com.lanchonete.salutem.bebida.controller;

import com.lanchonete.salutem.bebida.BebidaService;
import com.lanchonete.salutem.bebida.docs.BebidaControllerDocs;
import com.lanchonete.salutem.bebida.model.dto.BebidaRequest;
import com.lanchonete.salutem.bebida.model.dto.BebidaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bebidas")
public class BebidaController implements BebidaControllerDocs {

    private final BebidaService service;

    public BebidaController(BebidaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BebidaResponse> findById(@PathVariable Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BebidaResponse>> findByDescricao(@RequestParam String descricao) {
        var response = service.findByDescricao(descricao);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BebidaResponse>> findAll() {
        var response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BebidaResponse> save(@Valid @RequestBody BebidaRequest request) {
        var response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BebidaResponse> update(@PathVariable Long id, @Valid @RequestBody BebidaRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }
}
