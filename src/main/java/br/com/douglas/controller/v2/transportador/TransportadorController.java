package br.com.douglas.controller.v2.transportador;


import br.com.douglas.controller.core.BaseController;
import br.com.douglas.controller.core.BaseControllerConstants;
import br.com.douglas.entity.entities.temp.Transportador;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.transportador.TransportadorFromSelect;
import br.com.douglas.mapper.transportador.TransportadorRequest;
import br.com.douglas.mapper.transportador.TransportadorResponse;
import br.com.douglas.mapper.veiculo.VeiculoFromList;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import br.com.douglas.service.interfaces.IBaseService;
import br.com.douglas.service.transportador.TransportadorService;
import br.com.douglas.service.veiculo.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController("transportadorV2")
@RequestMapping("/api/v2/transportador")
public class TransportadorController extends BaseController<Transportador, TransportadorRequest, TransportadorResponse> {



    protected TransportadorController(IBaseService<Transportador> service, BaseMapper<Transportador, TransportadorRequest, TransportadorResponse> responseMapper) {
        super(service, responseMapper);
    }

    @GetMapping("/find-list-from-select")
    @PageableAsQueryParam
    @Operation(description = "Retorna lista de Transportador somente com Id e Nome, consulta rápida para atender o select")
    public List<TransportadorFromSelect> findListCriteria(@SortDefault.SortDefaults({@SortDefault(sort = "nome", direction = Sort.Direction.ASC)}) Sort sort) {
        return ((TransportadorService)service).findAllFromSelect(sort);
    }
}
