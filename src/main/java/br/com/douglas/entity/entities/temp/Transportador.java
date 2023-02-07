package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TRS_TRANSPORTADOR")
public class Transportador extends BaseEntity {
    @Id
    @Column(name = "TRS_ID", nullable = false)
    private String id;
    @Column(name = "TRS_DS_NOME")
    private String nome;
    @Column(name = "TRS_DS_RAZAO_SOCIAL")
    private String razaoSocial;
    @CNPJ
    @Column(name = "TRS_NM_CNPJ", unique = true)
    private String cnpj;
    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "END_ID", nullable = false)
    private Endereco endereco;
    @Column(name = "TRS_ST_ATIVO", nullable = false)
    private Boolean ativo;
    @Column(name = "TRS_DS_TELEFONE")
    private String telefone;
    @Column(name = "TRS_DS_EMAIL")
    private String email;
    @OneToMany(mappedBy = "transportador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Veiculo> veiculos;
}
