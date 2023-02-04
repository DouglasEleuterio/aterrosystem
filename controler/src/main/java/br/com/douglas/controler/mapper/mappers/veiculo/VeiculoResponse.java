package br.com.douglas.controler.mapper.mappers.veiculo;

import br.com.douglas.controler.mapper.mappers.modelo.ModeloResponse;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoResponse {
    private String id;
    private LocalDate dataLancamento;
    private LocalDateTime dataFabricacao;
    private ModeloResponse modelo;
}
