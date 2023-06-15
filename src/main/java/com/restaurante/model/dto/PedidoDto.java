package com.restaurante.model.dto;

import com.restaurante.model.type.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoDto {

    private List<String> produtos;
    private String codigo;
    private StatusPedido statusPedido;
}
