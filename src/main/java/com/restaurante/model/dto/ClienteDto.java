package com.restaurante.model.dto;

import com.restaurante.model.type.TipoDocumento;
import com.restaurante.model.type.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto {

    private Long id;
    private String nome;
    private String documento;
    private TipoDocumento tipoDocumento;
    private String celular;
    private String email;
    private TipoPessoa tipoPessoa;
    private UserDto userDto;
    private Integer pagina;
}
