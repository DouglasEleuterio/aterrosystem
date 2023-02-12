package br.com.douglas.mapper.combo;


import br.com.douglas.mapper.tipodescarte.TipoDescarteRequest;
import br.com.douglas.mapper.transportador.TransportadorRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
