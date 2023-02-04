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
@Table(name = "MTR_MOTORISTA")
public class Motorista extends BaseEntity {
    @Id
    @Column(name = "MTR_ID", nullable = false)
    private String id;
    @Column(name = "MTR_DS_NOME", nullable = false)
    private String nome;
    @Column(name = "MTR_NM_TELEFONE", nullable = false)
    private String telefone;
    @Column(name = "MTR_NM_CNH")
    private String cnh;
}
