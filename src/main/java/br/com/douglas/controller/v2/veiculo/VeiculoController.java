package br.com.douglas.controller.v2.veiculo;


import br.com.douglas.controller.core.BaseController;
import br.com.douglas.controller.core.BaseControllerConstants;
import br.com.douglas.entity.entities.temp.Veiculo;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.veiculo.VeiculoFromList;
import br.com.douglas.mapper.veiculo.VeiculoFromSelect;
import br.com.douglas.mapper.veiculo.VeiculoRequest;
import br.com.douglas.mapper.veiculo.VeiculoResponse;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import br.com.douglas.service.interfaces.IBaseService;
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

@RestController(value = "veiculoV2")
@RequestMapping("/api/v2/veiculo")
public class VeiculoController extends BaseController<Veiculo, VeiculoRequest, VeiculoResponse> {

    private final VeiculoService veiculoService;


    protected VeiculoController(IBaseService<Veiculo> service, BaseMapper<Veiculo, VeiculoRequest, VeiculoResponse> responseMapper, VeiculoService veiculoService) {
        super(service, responseMapper);
        this.veiculoService = veiculoService;
    }

    @GetMapping("/find-list-from-select")
    @PageableAsQueryParam
    @Operation(description = "Retorna lista de Placa com Id do veículo, consulta rápida para atender o select")
    public List<VeiculoFromSelect> findListCriteria(@SortDefault.SortDefaults({@SortDefault(sort = "placa", direction = Sort.Direction.ASC)}) Sort sort) {

        return ((VeiculoService)service).findAllFromSelect(sort);
    }

    @GetMapping("/find-by-list")
    public Page<VeiculoFromList> findPageV2(@Parameter(description = BaseControllerConstants.FIND_PAGE_DOC)
                                          @RequestParam(required = false) String search,
                                          @SortDefault.SortDefaults({@SortDefault(sort = "placa", direction = Sort.Direction.ASC)})
                                          @PageableDefault(size = 5) Pageable pageable) {
        if(Objects.nonNull(search) && !search.isBlank() && !search.isEmpty()) {
            return veiculoService.findAllFromList(SpecificationUtils.rsqlToSpecification(search), pageable);
        }
        return veiculoService.findAllFromList(null, pageable);
    }
}
