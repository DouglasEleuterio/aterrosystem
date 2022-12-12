package br.com.douglas.aterrosystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(optional = false)
    private Transportador transportador;
    @ManyToOne(optional = false)
    private TipoDescarte tipoDescarte;
    private Integer saldo;
    private Boolean ativo;
}
