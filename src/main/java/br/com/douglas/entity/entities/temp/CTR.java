package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CTR_CTR")
public class CTR extends BaseEntity {
    @Id
    @Column(name = "CTR_ID", nullable = false)
    private String id;

    @Column(name = "CTR_NM_NUMERO", unique = true)
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "GRD_ID")
    private Gerador gerador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "VCL_ID")
    private Veiculo veiculo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DST_ID")
    private Destinatario destinatario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TRS_ID")
    private Transportador transportador;

    @OneToMany(mappedBy = "ctr", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CTT_CTR_TIPO_DESCARTE", joinColumns = {
            @JoinColumn(name = "CTR_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CTT_CTR"))},
            inverseJoinColumns = {
            @JoinColumn(name = "TPS_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CTT_TPS"))
            })
    private List<TipoDescarte> tipoDescartes;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "MTR_ID", foreignKey = @ForeignKey(name = "FK_CTR_MTR"))
    private Motorista motorista;

    @Column(name = "CTR_DH_GERACAO")
    private LocalDateTime geracao;

    @Column(name = "CTR_ST_ATIVO")
    private Boolean ativo;
}