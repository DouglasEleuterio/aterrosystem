package br.com.douglas.controler.mapper.mappers.modelo;

import br.com.douglas.controler.mapper.mappers.marca.MarcaResponse;
import br.com.douglas.entity.enums.Categoria;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModeloResponse {
    private String id;
    private String nome;
    private MarcaResponse marca;
    private Categoria categoria;
}
