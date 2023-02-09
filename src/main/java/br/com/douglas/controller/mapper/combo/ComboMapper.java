package br.com.douglas.controller.mapper.combo;

import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Combo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComboMapper extends BaseMapper<Combo, ComboRequest, ComboResponse> {
}
