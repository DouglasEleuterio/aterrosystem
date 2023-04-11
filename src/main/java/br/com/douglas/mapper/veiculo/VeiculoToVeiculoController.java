package br.com.douglas.mapper.veiculo;

import br.com.douglas.mapper.transportador.TransportadorToVeiculoResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoToVeiculoController {

    private String id;
    private String placa;
    private Boolean ativo;
    private TransportadorToVeiculoResponse transportador;
}
