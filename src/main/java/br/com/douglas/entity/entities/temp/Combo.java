package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CMB_COMBO")
public class Combo extends BaseEntity {

    @Id
    @Column(name = "CMB_ID", nullable = false)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRS_ID", nullable = false)
    private Transportador transportador;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TDS_ID", nullable = false)
    private TipoDescarte tipoDescarte;
    @Column(name = "CMB_VL_SALDO", nullable = false)
    private Integer saldo;
    @Column(name = "CMB_ST_ATIVO", nullable = false)
    private Boolean ativo;
}
