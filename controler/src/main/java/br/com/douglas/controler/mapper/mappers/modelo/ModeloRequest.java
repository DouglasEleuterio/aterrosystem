package br.com.douglas.controler.mapper.mappers.modelo;

import br.com.douglas.controler.mapper.mappers.marca.MarcaRequest;
import br.com.douglas.controler.mapper.mappers.marca.MarcaResponse;
import br.com.douglas.controler.mapper.mappers.veiculo.VeiculoRequest;
import br.com.douglas.controler.mapper.mappers.veiculo.VeiculoResponse;
import br.com.douglas.entity.enums.Categoria;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModeloRequest {

    private String nome;
    private MarcaRequest marca;
    private Categoria categoria;
}
