package com.restaurante.controller;

import com.restaurante.model.dto.EntregaDto;
import com.restaurante.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entrega")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping("/adicionar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> adicionar(@RequestBody EntregaDto entregaDto) {
        return entregaService.adicionar(entregaDto);
    }

    @PutMapping("/editar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> editar(@RequestBody EntregaDto entregaDto) {
        return entregaService.editar(entregaDto);
    }

    @DeleteMapping("/excluir")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> excluir(@RequestBody EntregaDto entregaDto) {
        return entregaService.excluir(entregaDto);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> listar(@RequestBody EntregaDto entregaDto) {
        return entregaService.listar(entregaDto);
    }
}
