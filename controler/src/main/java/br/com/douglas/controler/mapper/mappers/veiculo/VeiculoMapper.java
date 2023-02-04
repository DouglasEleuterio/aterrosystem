package br.com.douglas.controler.mapper.mappers.veiculo;

import br.com.douglas.controler.mapper.BaseMapper;
import br.com.douglas.controler.mapper.mappers.modelo.ModeloMapper;
import br.com.douglas.entity.entities.Veiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculoMapper extends BaseMapper<Veiculo, VeiculoRequest, VeiculoResponse> {
}
