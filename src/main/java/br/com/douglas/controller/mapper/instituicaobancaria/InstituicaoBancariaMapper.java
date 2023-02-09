package br.com.douglas.controller.mapper.instituicaobancaria;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.InstituicaoBancaria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstituicaoBancariaMapper extends BaseMapper<InstituicaoBancaria, InstituicaoBancariaRequest, InstituicaoBancariaResponse> {
}
