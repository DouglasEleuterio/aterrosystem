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
@Table(name = "VCL_VEICULO")
public class Veiculo extends BaseEntity {

    @Id
    @Column(name = "VCL_ID", nullable = false)
    private String id;
    @Column(name = "VCL_DS_MARCA", nullable = false)
    private String marca;
    @Column(name = "VCL_DS_MODELO", nullable = false)
    private String modelo;
    @Column(name = "VCL_NM_PLACA", nullable = false, unique = true)
    private String placa;
    @ManyToOne(optional = false)
    @JoinColumn(name = "TRS_ID")
    private Transportador transportador;
    @Column(name = "VCL_ST_ATIVO", nullable = false, length = 1)
    private Boolean ativo;
}
