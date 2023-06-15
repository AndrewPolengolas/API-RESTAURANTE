package com.restaurante.controller;

import com.restaurante.model.dto.ClienteDto;
import com.restaurante.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/adicionar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> adicionar(@RequestBody ClienteDto clienteDto) {
        return clienteService.adicionar(clienteDto);
    }

    @PutMapping("/editar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> alterar(@RequestBody ClienteDto clienteDto) {
        return clienteService.alterar(clienteDto);
    }

    @DeleteMapping("/excluir")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> excluir(@RequestBody ClienteDto clienteDto) {
        return clienteService.excluir(clienteDto);
    }


    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listar(@RequestBody ClienteDto clienteDto) {
        return clienteService.listar(clienteDto);
    }

}
