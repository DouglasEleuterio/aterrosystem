package br.com.douglas.controller.mapper.transportador;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Transportador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransportadorMapper extends BaseMapper<Transportador, TransportadorRequest, TransportadorResponse> {
}
