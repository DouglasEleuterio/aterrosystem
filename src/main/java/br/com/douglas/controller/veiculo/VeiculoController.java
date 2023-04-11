package br.com.douglas.controller.veiculo;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.controller.core.BaseControllerConstants;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.veiculo.*;
import br.com.douglas.entity.entities.temp.Veiculo;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import br.com.douglas.service.interfaces.IBaseService;
import br.com.douglas.service.veiculo.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/veiculo")
public class VeiculoController extends BaseController<Veiculo, VeiculoRequest, VeiculoResponse> {

    private final VeiculoMapper veiculoMapper;
    protected VeiculoController(IBaseService<Veiculo> service, BaseMapper<Veiculo, VeiculoRequest, VeiculoResponse> responseMapper, VeiculoMapper veiculoMapper) {
        super(service, responseMapper);
        this.veiculoMapper = veiculoMapper;
    }

    @GetMapping("/find-list")
    @PageableAsQueryParam
    @Operation(description = "Consulta os dados e filtrando utilizando o padrão **RSQL** sem paginação")
    public List<VeiculoResponse> findList(@Parameter(description = BaseControllerConstants.FIND_PAGE_DOC)
                                                @RequestParam(required = false) String search,
                                                @SortDefault.SortDefaults({@SortDefault(sort = "placa", direction = Sort.Direction.ASC)}) Sort sort) {
        if(Objects.nonNull(search) && !search.isBlank() && !search.isEmpty()) {
            return service.findAll(SpecificationUtils.rsqlToSpecification(search), sort).stream().map(mapper::toResponse).toList();
        }
        return service.findAll(sort).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/find-list-dto")
    @PageableAsQueryParam
    @Operation(description = "Consulta os dados e filtrando utilizando o padrão **RSQL** sem paginação")
    public List<VeiculoToVeiculoController> findListDto(@Parameter(description = BaseControllerConstants.FIND_PAGE_DOC)
                                                        @RequestParam(required = false) String search,
                                                        @SortDefault.SortDefaults({@SortDefault(sort = "placa", direction = Sort.Direction.ASC)}) Sort sort) {
        if(Objects.nonNull(search) && !search.isBlank() && !search.isEmpty()) {
            return service.findAll(SpecificationUtils.rsqlToSpecification(search), sort).stream().map(veiculoMapper::toVeiculoController).toList();
        }
        return service.findAll(sort).stream().map(veiculoMapper::toVeiculoController).toList();
    }

    @GetMapping("/find-list-from-select")
    @PageableAsQueryParam
    @Operation(description = "Retorna lista de Placa com Id do veículo, consulta rápida para atender o select")
    public List<VeiculoFromSelect> findListCriteria(@SortDefault.SortDefaults({@SortDefault(sort = "placa", direction = Sort.Direction.ASC)}) Sort sort) {

        return ((VeiculoService)service).findAllFromSelect(sort);
    }

}
