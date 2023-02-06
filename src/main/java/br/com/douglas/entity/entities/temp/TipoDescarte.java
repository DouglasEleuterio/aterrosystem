package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "TDS_TIPO_DESCARTE")
public class TipoDescarte extends BaseEntity {

    @Id
    @Column(name = "TDS_ID", nullable = false)
    private String id;
    @Column(name = "TDS_DS_NOME", nullable = false)
    private String nome;
    @Column(name = "TDS_VL_PRECO", nullable = false)
    private Double valor;
    @Column(name = "TDS_ST_ATIVO", nullable = false)
    private Boolean ativo;
}



