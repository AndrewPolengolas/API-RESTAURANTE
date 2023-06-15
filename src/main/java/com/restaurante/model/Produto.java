package com.restaurante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.restaurante.model.type.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "produto", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "codigo"
        })
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", sequenceName = "GEN_USER_DETAIL_ID", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String codigo;

    private LocalDateTime dataIni;

    private LocalDateTime dataAlt;

    private LocalDateTime dataFim;

    private String nome;

    private Double preco;

    @Size(max = 600)
    private String descricao;

    @JsonIgnore
    @ManyToMany(mappedBy = "produtoList", fetch = FetchType.LAZY)
    private List<Pedido> pedidoList;
}