package br.com.douglas.aterrosystem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aquisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    private Combo combo;
    @ManyToOne
    private FormaPagamento formaPagamento;
    private Integer quantidadeAdquirida;
    private LocalDate dataPagamento;
    private Double valorPago;
    private Double desconto;
    private Boolean ativo;
}
