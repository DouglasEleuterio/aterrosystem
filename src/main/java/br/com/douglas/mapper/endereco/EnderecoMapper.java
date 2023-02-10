package br.com.douglas.mapper.endereco;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper extends BaseMapper<Endereco, EnderecoRequest, EnderecoResponse> {
}
