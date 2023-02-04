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
@Table(name = "FRP_FORMA_PAGAMENTO")
public class FormaPagamento extends BaseEntity {

    @Id
    @Column(name = "FRP_ID", nullable = false)
    private String id;
    @Column(name = "FRP_DS_NOME", nullable = false)
    private String nome;
    @Column(name = "FRP_ST_ATIVO", nullable = false)
    private Boolean ativo;
}