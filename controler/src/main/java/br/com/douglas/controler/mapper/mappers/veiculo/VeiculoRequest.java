package br.com.douglas.controler.mapper.mappers.veiculo;

import br.com.douglas.controler.mapper.mappers.modelo.ModeloRequest;
import br.com.douglas.entity.entities.Modelo;
import br.com.douglas.entity.enums.Categoria;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoRequest  {

    private LocalDate dataLancamento;
    private LocalDateTime dataFabricacao;
    private ModeloRequest modelo;
}
