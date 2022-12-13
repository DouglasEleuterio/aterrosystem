package br.com.douglas.aterrosystem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "descartes_por_combo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescartePorCombo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Combo combo;
    @OneToOne
    private CTR ctr;
    private Integer quantidade;
    private LocalDateTime dataDescarte;

}
