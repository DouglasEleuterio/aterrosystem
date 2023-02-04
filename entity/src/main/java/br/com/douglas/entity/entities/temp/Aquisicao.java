package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AQS_AQUISICAO")
public class Aquisicao extends BaseEntity {

    @Id
    @Column(name = "AQS_ID", nullable = false)
    private String id;
    @OneToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "CMB_ID")
    private Combo combo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FRP_ID", nullable = false)
    private FormaPagamento formaPagamento;
    @Column(name = "AQS_QT_ADQUIRIDA")
    private Integer quantidadeAdquirida;
    @Column(name = "AQS_DH_PAGAMENTO")
    private LocalDate dataPagamento;
    @Column(name = "AQS_VL_PAGO")
    private Double valorPago;
    @Column(name = "AQS_VL_DESCONTO")
    private Double desconto;
    @Column(name = "AQS_ST_ATIVO")
    private Boolean ativo;
}
