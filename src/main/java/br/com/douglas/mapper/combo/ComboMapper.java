package br.com.douglas.mapper.combo;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Combo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComboMapper extends BaseMapper<Combo, ComboRequest, ComboResponse> {
}
