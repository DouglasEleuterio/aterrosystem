package br.com.douglas.controller.mapper.veiculo;

import br.com.douglas.controller.mapper.transportador.TransportadorResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class VeiculoResponse {

    private String id;
    private String marca;
    private String modelo;
    private String placa;
    @JsonIgnoreProperties("transportador.endereco")
    private TransportadorResponse transportador;
    private Boolean ativo;
}
