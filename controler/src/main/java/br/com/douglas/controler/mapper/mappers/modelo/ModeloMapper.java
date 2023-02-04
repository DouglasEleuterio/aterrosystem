package br.com.douglas.controler.mapper.mappers.modelo;

import br.com.douglas.controler.mapper.BaseMapper;
import br.com.douglas.controler.mapper.mappers.marca.MarcaMapper;
import br.com.douglas.entity.entities.Modelo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface ModeloMapper extends BaseMapper<Modelo, ModeloRequest, ModeloResponse> {
}
