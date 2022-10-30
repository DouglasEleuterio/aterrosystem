package br.com.douglas.aterrosystem.entity;

import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destinatario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String nome;
    private String razaoSocial;
    @CNPJ
    @Column(unique = true)
    private String cnpj;
    @OneToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Endereco enderecoRecebimento;
    @OneToMany
    private List<CTR> ctrList;
}
