package br.com.douglas.mapper.veiculo;

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
    private Boolean ativo;
}
