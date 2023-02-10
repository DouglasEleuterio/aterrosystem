package br.com.douglas.mapper.formapagamento;

import br.com.douglas.entity.entities.temp.FormaPagamento;
import br.com.douglas.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FormaPagamentoMapper extends BaseMapper<FormaPagamento, FormaPagamentoRequest, FormaPagamentoResponse> {
}
