package com.restaurante.repository;

import com.restaurante.model.Cliente;
import com.restaurante.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Pedido findByCodigo(String codigo);

    List<Pedido> findByCliente(Cliente cliente);
}
