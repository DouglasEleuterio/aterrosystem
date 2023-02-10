package br.com.douglas.mapper.veiculo;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Veiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculoMapper extends BaseMapper<Veiculo, VeiculoRequest, VeiculoResponse> {
}
