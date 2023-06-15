package com.restaurante.model.dto;

import com.restaurante.model.type.TipoPreco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDto {

    private Integer pagina;
    private String nome;
    private Double preco;
    private String descricao;
    private String codigo;
    private TipoPreco tipoPreco;
    private Double precoMin;
    private Double precoMax;
}
