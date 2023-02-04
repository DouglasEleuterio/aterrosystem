package br.com.douglas.entity.entities;

import br.com.douglas.entity.base.BaseEntity;
import br.com.douglas.entity.enums.Categoria;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modelo extends BaseEntity {

    @Id
    @Column(length = 36)
    private String id;

    private String nome;

    @Enumerated(EnumType.ORDINAL)
    private Categoria categoria;

    @OneToMany(mappedBy = "modelo")
    private Set<Veiculo> veiculos;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_modelo_marca"))
    private Marca marca;
}
