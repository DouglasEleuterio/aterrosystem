package br.com.douglas.controller.mapper.tipodescarte;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.TipoDescarte;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoDescarteMapper extends BaseMapper<TipoDescarte, TipoDescarteRequest, TipoDescarteResponse> {
}
