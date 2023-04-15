package br.com.douglas.controller.v2.veiculo;


import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Veiculo;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.veiculo.VeiculoFromSelect;
import br.com.douglas.mapper.veiculo.VeiculoRequest;
import br.com.douglas.mapper.veiculo.VeiculoResponse;
import br.com.douglas.service.interfaces.IBaseService;
import br.com.douglas.service.veiculo.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "veiculoV2")
@RequestMapping("/api/v2/veiculo")
public class VeiculoController extends BaseController<Veiculo, VeiculoRequest, VeiculoResponse> {


    protected VeiculoController(IBaseService<Veiculo> service, BaseMapper<Veiculo, VeiculoRequest, VeiculoResponse> responseMapper) {
        super(service, responseMapper);
    }

    @GetMapping("/find-list-from-select")
    @PageableAsQueryParam
    @Operation(description = "Retorna lista de Placa com Id do veículo, consulta rápida para atender o select")
    public List<VeiculoFromSelect> findListCriteria(@SortDefault.SortDefaults({@SortDefault(sort = "placa", direction = Sort.Direction.ASC)}) Sort sort) {

        return ((VeiculoService)service).findAllFromSelect(sort);
    }
}
