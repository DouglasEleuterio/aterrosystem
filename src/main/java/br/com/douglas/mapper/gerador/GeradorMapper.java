package br.com.douglas.mapper.gerador;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Gerador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeradorMapper extends BaseMapper<Gerador, GeradorRequest, GeradorResponse> {
}
