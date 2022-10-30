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
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate dataPagamento;
    private Double valor;
    @ManyToOne(optional = false)
    private FormaPagamento formaPagamento;
    private boolean ativo;
    @ManyToOne
    private CTR ctr;
}
