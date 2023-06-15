package com.restaurante.controller;

import com.restaurante.model.dto.ProdutoDto;
import com.restaurante.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<?> listar(@RequestBody ProdutoDto produtoDto) {
        return produtoService.listar(produtoDto);
    }

    @PostMapping("/adicionar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adicionar(@RequestBody ProdutoDto produtoDto) {
        return produtoService.adicionar(produtoDto);
    }

    @PutMapping("/editar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editar(@RequestBody ProdutoDto produtoDto) {
        return produtoService.editar(produtoDto);
    }

    @DeleteMapping("/excluir")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> excluir(@RequestBody ProdutoDto produtoDto) {
        return produtoService.excluir(produtoDto);
    }

}
