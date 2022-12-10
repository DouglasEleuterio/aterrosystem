package br.com.douglas.aterrosystem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CTR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(optional = false)
    private Gerador gerador;
    @ManyToOne(optional = false)
    private Veiculo veiculo;
    @ManyToOne(optional = false)
    private Destinatario destinatario;
    @ManyToOne(optional = false)
    private Transportador transportador;
    @OneToMany(mappedBy = "ctr", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos;
    @ManyToMany()
    private List<TipoDescarte> tipoDescartes;
    @ManyToOne(optional = false)
    private Motorista motorista;
    @Column
    private LocalDate geracao;
    @Column(name = "ativo", nullable = true)
    private Boolean ativo;
}
