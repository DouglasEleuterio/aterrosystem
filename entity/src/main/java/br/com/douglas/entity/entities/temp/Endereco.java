package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "END_ENDERECO")
public class Endereco extends BaseEntity {
    @Id
    @Column(name = "END_ID", nullable = false)
    private String id;
    @Column(name = "END_DS_LOGRADOURO")
    private String logradouro;
    @Column(name = "END_DS_NUMERO")
    private String numero;
    @Column(name = "END_DS_COMPLEMENTO")
    private String complemento;
    @Column(name = "END_DS_BAIRRO")
    private String bairro;
    @Column(name = "END_DS_CIDADE")
    private String cidade;
    @Column(name = "END_DS_ESTADO")
    private String estado;
    @Column(name = "END_DS_CEP")
    private String cep;
    @Column(name = "END_DS_OBSERVACAO")
    private String observacao;
}
