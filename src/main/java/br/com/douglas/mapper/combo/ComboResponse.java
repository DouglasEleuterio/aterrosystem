package br.com.douglas.mapper.combo;

import br.com.douglas.mapper.tipodescarte.TipoDescarteResponse;
import br.com.douglas.mapper.transportador.TransportadorResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboResponse {

    private String id;
    private TransportadorResponse transportador;
    private TipoDescarteResponse tipoDescarte;
    private Integer saldo;
    private Boolean ativo;

}
