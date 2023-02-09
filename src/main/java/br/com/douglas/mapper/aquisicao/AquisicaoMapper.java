package br.com.douglas.mapper.aquisicao;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Aquisicao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AquisicaoMapper extends BaseMapper<Aquisicao, AquisicaoRequest, AquisicaoResponse> {
}
