package br.com.douglas.mapper.pagamento;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Pagamento;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagamentoMapper extends BaseMapper<Pagamento, PagamentoRequest, PagamentoResponse> {

    List<PagamentoForTableResponse> forTableResponse(List<Pagamento> pagamentos);
}
