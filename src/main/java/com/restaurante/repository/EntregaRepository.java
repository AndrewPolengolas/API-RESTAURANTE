package com.restaurante.repository;

import com.restaurante.model.Cliente;
import com.restaurante.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    Entrega findByCodigo(String codigo);

    List<Entrega> findByCliente(Cliente cliente);
}
