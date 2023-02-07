package br.com.douglas.controller.mapper.endereco;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Endereco;
import br.com.douglas.entity.entities.temp.Transportador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper extends BaseMapper<Endereco, EnderecoRequest, EnderecoResponse> {
}
