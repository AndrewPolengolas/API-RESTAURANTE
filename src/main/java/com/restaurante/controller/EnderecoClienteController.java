package com.restaurante.controller;

import com.restaurante.model.dto.EnderecoDto;
import com.restaurante.service.EnderecoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/endereco")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EnderecoClienteController {

    @Autowired
    private EnderecoClienteService enderecoClienteService;

    @PostMapping("/adicionar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> adicionar(@RequestBody EnderecoDto enderecoDto) {
        return enderecoClienteService.adicionar(enderecoDto);
    }

    @PutMapping("/editar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> editar(@RequestBody EnderecoDto enderecoDto) {
        return enderecoClienteService.editar(enderecoDto);
    }

    @DeleteMapping("/excluir")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> excluir(@RequestBody EnderecoDto enderecoDto) {
        return enderecoClienteService.excluir(enderecoDto);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> listar() {
        return enderecoClienteService.listar();
    }
}
