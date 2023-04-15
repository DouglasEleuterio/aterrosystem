package br.com.douglas.controller.v1.gerador;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.controller.core.BaseControllerConstants;
import br.com.douglas.entity.entities.temp.Gerador;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.gerador.GeradorRequest;
import br.com.douglas.mapper.gerador.GeradorResponse;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import br.com.douglas.service.interfaces.IBaseService;
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
@RequestMapping("/api/gerador")
public class GeradorController extends BaseController<Gerador, GeradorRequest, GeradorResponse> {

    protected GeradorController(IBaseService<Gerador> service, BaseMapper<Gerador, GeradorRequest, GeradorResponse> responseMapper) {
        super(service, responseMapper);
    }

    @GetMapping("/find-list")
    @PageableAsQueryParam
    @Operation(description = "Consulta os dados e filtrando utilizando o padrão **RSQL** sem paginação")
    public List<GeradorResponse> findList(@Parameter(description = BaseControllerConstants.FIND_PAGE_DOC)
                                                @RequestParam(required = false) String search,
                                                @SortDefault.SortDefaults({@SortDefault(sort = "nome", direction = Sort.Direction.ASC)}) Sort sort) {
        if(Objects.nonNull(search) && !search.isBlank() && !search.isEmpty()) {
            return service.findAll(SpecificationUtils.rsqlToSpecification(search), sort).stream().map(mapper::toResponse).toList();
        }
        return service.findAll(sort).stream().map(mapper::toResponse).toList();
    }

}
