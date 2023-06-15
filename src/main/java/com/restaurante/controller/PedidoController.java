package com.restaurante.controller;

import com.restaurante.model.dto.PedidoDto;
import com.restaurante.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/adicionar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> criarPedido(@RequestBody PedidoDto pedidoDto) {
        return pedidoService.adicionar(pedidoDto);
    }

    @PutMapping("/editar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> editar(@RequestBody PedidoDto pedidoDto) {
        return pedidoService.editar(pedidoDto);
    }

    @DeleteMapping("/excluir")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> excluir(@RequestBody PedidoDto pedidoDto) {
        return pedidoService.excluir(pedidoDto);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> listar(@RequestBody PedidoDto pedidoDto) {
        return pedidoService.listar(pedidoDto);
    }
}
