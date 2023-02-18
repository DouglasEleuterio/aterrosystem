package br.com.douglas.mapper.combo;


import br.com.douglas.mapper.pagamento.PagamentoRequest;
import br.com.douglas.mapper.tipodescarte.TipoDescarteRequest;
import br.com.douglas.mapper.transportador.TransportadorRequest;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboRequest {

    private String id;
    private TransportadorRequest transportador;
    private TipoDescarteRequest tipoDescarte;
    private Integer saldo;
    private Boolean ativo;

    private Set<PagamentoRequest> pagamentos;
}
