package br.com.douglas.controller.mapper.aquisicao;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Aquisicao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AquisicaoMapper extends BaseMapper<Aquisicao, AquisicaoRequest, AquisicaoResponse> {
}
