package br.com.douglas.mapper.ctr;

import br.com.douglas.mapper.destinatario.DestinatarioResponse;
import br.com.douglas.mapper.gerador.GeradorResponse;
import br.com.douglas.mapper.motorista.MotoristaResponse;
import br.com.douglas.mapper.pagamento.PagamentoResponse;
import br.com.douglas.mapper.tipodescarte.TipoDescarteResponse;
import br.com.douglas.mapper.transportador.TransportadorResponse;
import br.com.douglas.mapper.veiculo.VeiculoResponse;
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
    private GeradorResponse gerador;
    private VeiculoResponse veiculo;
    private DestinatarioResponse destinatario;
    private TransportadorResponse transportador;
    private List<PagamentoResponse> pagamentos;
    private List<TipoDescarteResponse> tipoDescartes;
    private MotoristaResponse motorista;
    private LocalDateTime geracao;
    private Boolean ativo;
}
