package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "GRD_GERADOR")
public class Gerador extends BaseEntity {
    @Id
    @Column(name = "GRD_ID", nullable = false)
    private String id;
    @Column(name = "GRD_DS_NOME", nullable = false)
    private String nome;
    @Column(name = "GRD_DS_RAZAO_SOCIAL")
    private String razaoSocial;
    @CNPJ
    @Column(name = "GRD_DS_CNPJ", unique = true)
    private String cnpj;
    @CPF
    @Column(name = "GRD_DS_CPF", nullable = true, unique = true)
    private String cpf;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "END_ID")
    private Endereco retirada;
    @Column(name = "GRD_DS_TELEFONE")
    private String telefone;
    @Email
    @Column(name = "GRD_DS_EMAIL")
    private String email;
    @Column(name = "GRD_ST_ATIVO", nullable = false)
    private Boolean ativo;
}
