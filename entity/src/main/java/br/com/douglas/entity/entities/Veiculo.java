package br.com.douglas.entity.entities;

import br.com.douglas.entity.base.BaseEntity;
import br.com.douglas.entity.enums.Categoria;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veiculo extends BaseEntity {

    @Id
    @Column(length = 36)
    private String id;

    private LocalDate dataLancamento;

    private LocalDateTime dataFabricacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_veiculo_modelo"))
    private Modelo modelo;
}
