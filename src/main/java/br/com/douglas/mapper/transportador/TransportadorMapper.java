package br.com.douglas.mapper.transportador;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Transportador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransportadorMapper extends BaseMapper<Transportador, TransportadorRequest, TransportadorResponse> {
}
