package br.com.douglas.mapper.pagamento;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Pagamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PagamentoMapper extends BaseMapper<Pagamento, PagamentoRequest, PagamentoResponse> {
}
