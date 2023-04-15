package br.com.douglas.mapper.veiculo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class VeiculoFromList {

    private String id;
    private String placa;
    private String transportador;
    private boolean ativo;
}