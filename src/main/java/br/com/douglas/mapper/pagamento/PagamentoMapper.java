package br.com.douglas.mapper.pagamento;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.mapper.IdAndNameResponse;
import br.com.douglas.model.entity.BaseEntityResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PagamentoMapper extends BaseMapper<Pagamento, PagamentoRequest, PagamentoResponse> {

    PagamentoForTableResponse forTableResponse(Pagamento pagamento);
}
