package br.com.douglas.mapper.destinatario;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Destinatario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DestinatarioMapper extends BaseMapper<Destinatario, DestinatarioRequest, DestinatarioResponse> {
}
