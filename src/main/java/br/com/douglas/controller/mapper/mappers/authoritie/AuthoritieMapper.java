package br.com.douglas.controller.mapper.mappers.authoritie;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthoritieMapper extends BaseMapper<Authority, AuthorityRequest, AuthorityResponse> {
}
