package br.com.douglas.mapper.ctr;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.CTR;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CtrMapper extends BaseMapper<CTR, CtrRequest, CtrResponse> {
}
