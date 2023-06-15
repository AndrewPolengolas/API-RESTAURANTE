package com.restaurante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante.model.type.Status;
import com.restaurante.model.type.TipoEndereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", sequenceName = "GEN_USER_DETAIL_ID", allocationSize = 1)
    private Long id;

    private Status status;

    private LocalDateTime dataIni;

    private LocalDateTime dataAlt;

    private LocalDateTime dataFim;

    private String cep;

    private String logradouro;

    private String complemento;

    @Enumerated(EnumType.STRING)
    private TipoEndereco tipoEndereco;

    private Long numero;

    private String cidade;

    private String estado;

    private String pais;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

}
