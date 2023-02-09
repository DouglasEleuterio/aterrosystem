package br.com.douglas.mapper.mappers.user;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface UserMapper extends BaseMapper<User, UserRequest, UserResponse> {
}
