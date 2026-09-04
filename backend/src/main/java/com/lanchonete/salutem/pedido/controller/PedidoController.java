package com.lanchonete.salutem.pedido.controller;

import com.lanchonete.salutem.pedido.PedidoService;
import com.lanchonete.salutem.pedido.model.dto.PedidoRequest;
import com.lanchonete.salutem.pedido.model.dto.PedidoResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{id}")
    public PedidoResponse getById(@PathVariable Long id) {
        return pedidoService.getById(id);
    }

    @GetMapping("/all")
    public List<PedidoResponse> getAll(){
        return pedidoService.getAll();
    }

    @PostMapping
    public PedidoResponse save(@Valid @RequestBody PedidoRequest request){
        return pedidoService.save(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        pedidoService.delete(id);
    }

    @PutMapping("/{id}")
    public PedidoResponse update(@PathVariable Long id, @Valid @RequestBody PedidoRequest request){
        return pedidoService.update(id, request);
    }


}
