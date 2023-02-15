package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PGT_PAGAMENTO")
public class Pagamento extends BaseEntity {
    @Id
    @Column(name = "PGT_ID", nullable = false)
    private String id;

    @Column(name = "PGT_DT_PAGAMENTO", nullable = false)
    private LocalDate dataPagamento;

    @Column(name = "PGT_VL_PRECO", nullable = false)
    private Double valor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FRP_ID")
    private FormaPagamento formaPagamento;

    @Column(name = "PGT_ST_ATIVO", nullable = false)
    private Boolean ativo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "CTR_ID", nullable = false)
    @JsonIgnore
    private CTR ctr;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IBC_ID", nullable = false)
    private InstituicaoBancaria instituicaoBancaria;
}
