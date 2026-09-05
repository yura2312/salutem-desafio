package com.lanchonete.salutem.hamburguer.controller;

import com.lanchonete.salutem.hamburguer.HamburguerService;
import com.lanchonete.salutem.hamburguer.model.dto.HamburguerRequest;
import com.lanchonete.salutem.hamburguer.model.dto.HamburguerResponse;
import com.lanchonete.salutem.hamburguer.docs.HamburguerControllerDocs;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hamburguers")
public class HamburguerController implements HamburguerControllerDocs {

    private final HamburguerService service;

    public HamburguerController(HamburguerService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<HamburguerResponse>> getAll() {
        var hamburguers = service.findAll();
        return ResponseEntity.ok(hamburguers);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<HamburguerResponse> getById(@PathVariable Long id){
        var hamburguer = service.findById(id);
        return ResponseEntity.ok(hamburguer);
    }

    @PostMapping
    @Override
    public ResponseEntity<HamburguerResponse> save(@Valid @RequestBody HamburguerRequest request){
        var hamburguer = service.save(request);
        return ResponseEntity.ok(hamburguer);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<HamburguerResponse> update(@PathVariable Long id, @Valid @RequestBody HamburguerRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<HamburguerResponse>> findByDescricao(@RequestParam String descricao){
      return ResponseEntity.ok(service.findByDescricao(descricao));
    }
}
