package com.lanchonete.salutem.hamburguer;

import com.lanchonete.salutem.hamburguer.model.dto.HamburguerRequest;
import com.lanchonete.salutem.hamburguer.model.dto.HamburguerResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hamburguers")
public class HamburguerController {

    private final HamburguerService service;

    public HamburguerController(HamburguerService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HamburguerResponse>> getAll() {
        var hamburguers = service.findAll();
        return ResponseEntity.ok(hamburguers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HamburguerResponse> getById(@PathVariable Long id){
        var hamburguer = service.findById(id);
        return ResponseEntity.ok(hamburguer);
    }

    @PostMapping
    public ResponseEntity<HamburguerResponse> save(@Valid @RequestBody HamburguerRequest request){
        var hamburguer = service.save(request);
        return ResponseEntity.ok(hamburguer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HamburguerResponse> update(@PathVariable Long id, @Valid @RequestBody HamburguerRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }
}
