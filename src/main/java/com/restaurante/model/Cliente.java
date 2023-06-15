package com.restaurante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante.model.type.Status;
import com.restaurante.model.type.TipoDocumento;
import com.restaurante.model.type.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "documento"
        })
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", sequenceName = "GEN_USER_DETAIL_ID", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dataIni;

    private LocalDateTime dataAlt;

    private LocalDateTime dataFim;

    private Long usuario;

    private String nome;

    private String documento;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    private String celular;

    private String email;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<Pedido> pedidos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<EnderecoCliente> enderecos;

    public void addPedido(Pedido pedido) {
        if (pedidos == null) {
            pedidos = new ArrayList<>();
        }
        pedidos.add(pedido);
        pedido.setCliente(this);
    }

    public void addEndereco(EnderecoCliente enderecoCliente) {
        if (enderecos == null) {
            enderecos = new ArrayList<>();
        }
        enderecos.add(enderecoCliente);
        enderecoCliente.setCliente(this);
    }
}
