package br.com.douglas.controller.mapper.veiculo;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Veiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculoMapper extends BaseMapper<Veiculo, VeiculoRequest, VeiculoResponse> {
}
