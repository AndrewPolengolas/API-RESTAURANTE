package com.restaurante.repository;

import com.restaurante.model.Cliente;
import com.restaurante.model.EnderecoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, Long> {

    List<EnderecoCliente> findByCliente(Cliente cliente);
}
