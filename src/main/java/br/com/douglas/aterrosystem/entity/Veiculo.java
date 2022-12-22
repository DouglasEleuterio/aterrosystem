package br.com.douglas.aterrosystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String marca;
    private String modelo;
    @Column(unique = true)
    private String placa;
    @ManyToOne(optional = false)
    private Transportador transportador;
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;
}
