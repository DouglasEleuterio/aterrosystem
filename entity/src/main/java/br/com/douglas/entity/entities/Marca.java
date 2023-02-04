package br.com.douglas.entity.entities;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marca extends BaseEntity {

    @Id
    @Column(length = 36)
    private String id;

    private String nome;

    @OneToMany(mappedBy = "marca")
    private Set<Modelo> modelos;
}
