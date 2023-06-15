package com.restaurante.model.dto;

import com.restaurante.model.type.TipoEndereco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDto {

    private Long id;
    private String cep;

    private String logradouro;

    private String complemento;

    private TipoEndereco tipoEndereco;

    private Long numero;

    private String cidade;

    private String estado;

    private String pais;
}
