package br.com.douglas.controller.mapper.mappers.user;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface UserMapper extends BaseMapper<User, UserRequest, UserResponse> {
}
