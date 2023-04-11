package br.com.douglas.mapper.veiculo;

import br.com.douglas.mapper.transportador.TransportadorToVeiculoResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoToVeiculoController {

    private String id;
    private String placa;
    @JsonIgnore
    private Boolean ativo;
    @JsonIgnore
    private TransportadorToVeiculoResponse transportador;
}
