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
public class Transportador {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String nome;
    private String razaoSocial;
    @CNPJ
    @Column(unique = true)
    private String cnpj;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    private Endereco endereco;
    @OneToMany
    private List<CTR> ctrList;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportador")
    private List<Veiculo> veiculos;
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;
    private String telefone;
    private String email;
}
