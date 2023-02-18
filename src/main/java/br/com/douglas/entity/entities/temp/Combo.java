package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TRS_ID", nullable = false)
    private Transportador transportador;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TDS_ID", nullable = false)
    private TipoDescarte tipoDescarte;
    @Column(name = "CMB_VL_SALDO", nullable = false)
    private Integer saldo;
    @Column(name = "CMB_ST_ATIVO", nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos;
}
