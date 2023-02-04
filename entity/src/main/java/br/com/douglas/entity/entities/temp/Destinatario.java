package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DST_DESTINATARIO")
public class Destinatario extends BaseEntity {
    @Id
    @Column(name = "DST_ID", nullable = false)
    private String id;
    @Column(name = "DST_DS_NOME", nullable = false)
    private String nome;
    @Column(name = "DST_DS_RAZAO_SOCIAL")
    private String razaoSocial;
    @CNPJ
    @Column(name = "DST_NM_CNPJ", length = 18)
    private String cnpj;
    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "END_ID")
    private Endereco enderecoRecebimento;
    @Column(name = "DST_NM_TELEFONE", nullable = false)
    private String telefone;
    @Email
    @Column(name = "DST_DS_EMAIL")
    private String email;
    @Column(name = "DST_ST_ATIVO", nullable = false)
    private Boolean ativo;
}