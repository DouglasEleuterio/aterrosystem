package br.com.douglas.controler.mapper.mappers.marca;

import br.com.douglas.controler.mapper.mappers.modelo.ModeloMapper;
import br.com.douglas.entity.entities.Marca;
import br.com.douglas.controler.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarcaMapper extends BaseMapper<Marca, MarcaRequest, MarcaResponse> {
}
