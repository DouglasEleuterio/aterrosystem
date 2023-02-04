package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "GRD_ID")
    private Gerador gerador;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "VCL_ID")
    private Veiculo veiculo;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "DST_ID")
    private Destinatario destinatario;
    @ManyToOne(optional = false)
    @JoinColumn(name = "TRS_ID")
    private Transportador transportador;
    @OneToMany(mappedBy = "ctr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "PGT_ID")
    private List<Pagamento> pagamentos;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CTT_CTR_TIPO_DESCARTE", joinColumns = {
            @JoinColumn(name = "CTR_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CTT_CTR"))},
            inverseJoinColumns = {
            @JoinColumn(name = "TPS_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CTT_TPS"))
            })
    private List<TipoDescarte> tipoDescartes;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "MTR_ID", foreignKey = @ForeignKey(name = "FK_CTR_MTR"))
    private Motorista motorista;
    @Column(name = "CTR_DH_GERACAO", nullable = false)
    private LocalDateTime geracao;
    @Column(name = "CTR_ST_ATIVO")
    private Boolean ativo;
}