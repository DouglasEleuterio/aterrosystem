package br.com.douglas.mapper.veiculo;

import br.com.douglas.model.entity.BaseEntityRequest;
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
public class VeiculoRequest {

    private String marca;
    private String modelo;
    private String placa;
    private BaseEntityRequest transportador;
    private Boolean ativo;
}
