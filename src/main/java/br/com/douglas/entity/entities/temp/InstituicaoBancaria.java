package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "IBC_INSTITUICAO_BANCARIA")
public class InstituicaoBancaria extends BaseEntity {

    @Id
    @Column(name = "IBC_ID", length = 26)
    private String id;

    @Column(name = "IBC_DS_NOME")
    private String nome;
    @Column(name = "IBC_NM_AGENCIA")
    private String agencia;
    @Column(name = "IBC_NM_CONTA")
    private String conta;
}
