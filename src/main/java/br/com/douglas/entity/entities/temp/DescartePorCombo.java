package br.com.douglas.entity.entities.temp;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DSC_DESCARTE_POR_COMBO")
public class DescartePorCombo extends BaseEntity {
    @Id
    @Column(name = "DSC_ID", nullable = false)
    private String id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CMB_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_DSC_CMB"))
    private Combo combo;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CTR_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_DSC_CTR"))
    private CTR ctr;
    @Column(name = "DSC_NM_QUANTIDADE", nullable = false)
    private Integer quantidade;
    @Column(name = "DSC_DH_DESCARTE", nullable = false)
    private LocalDateTime dataDescarte;
}
