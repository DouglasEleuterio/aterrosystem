package br.com.douglas.mapper.ctr;

import br.com.douglas.entity.entities.temp.Destinatario;
import br.com.douglas.entity.entities.temp.Gerador;
import br.com.douglas.entity.entities.temp.Motorista;
import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.entity.entities.temp.TipoDescarte;
import br.com.douglas.entity.entities.temp.Transportador;
import br.com.douglas.entity.entities.temp.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CtrResponse {

    private String id;
    private Integer numero;
    private Gerador gerador;
    private Veiculo veiculo;
    private Destinatario destinatario;
    private Transportador transportador;
    private List<Pagamento> pagamentos;
    private List<TipoDescarte> tipoDescartes;
    private Motorista motorista;
    private LocalDateTime geracao;
    private Boolean ativo;
}
