package br.com.douglas.controller.mapper.motorista;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Motorista;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MotoristaMapper extends BaseMapper<Motorista, MotoristaRequest, MotoristaResponse> {
}
