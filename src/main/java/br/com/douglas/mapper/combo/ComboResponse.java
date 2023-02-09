package br.com.douglas.mapper.combo;

import br.com.douglas.mapper.transportador.TransportadorResponse;
import br.com.douglas.entity.entities.temp.TipoDescarte;
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
public class ComboResponse {

    private String id;
    private TransportadorResponse transportador;
    private TipoDescarte tipoDescarte;
    private Integer saldo;
    private Boolean ativo;
}
