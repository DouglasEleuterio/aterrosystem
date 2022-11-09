package br.com.douglas.aterrosystem.entity;

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
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    private Transportador transportador;
}
