package br.com.douglas.mapper.combo;


import br.com.douglas.mapper.transportador.TransportadorRequest;
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
public class ComboRequest {

    private String id;
    private TransportadorRequest transportador;
    private TipoDescarte tipoDescarte;
    private Integer saldo;
    private Boolean ativo;
}
