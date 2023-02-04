package br.com.douglas.controler.mapper.mappers.authoritie;

import br.com.douglas.controler.mapper.BaseMapper;
import br.com.douglas.entity.entities.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthoritieMapper extends BaseMapper<Authority, AuthorityRequest, AuthorityResponse> {
}
