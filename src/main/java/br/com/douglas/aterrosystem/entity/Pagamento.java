package br.com.douglas.aterrosystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate dataPagamento;
    private Double valor;
    @ManyToOne(optional = false)
    private FormaPagamento formaPagamento;
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private CTR ctr;
}
