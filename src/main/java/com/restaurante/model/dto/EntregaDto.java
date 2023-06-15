package com.restaurante.model.dto;

import com.restaurante.model.type.StatusEntrega;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaDto {

    private String codigo;
    private StatusEntrega statusEntrega;
    private String pedido;
    private Long endereco;
}
