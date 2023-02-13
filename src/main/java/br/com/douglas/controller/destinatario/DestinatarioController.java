package br.com.douglas.controller.destinatario;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.controller.core.BaseControllerConstants;
import br.com.douglas.entity.entities.temp.Destinatario;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.destinatario.DestinatarioMapper;
import br.com.douglas.mapper.destinatario.DestinatarioRequest;
import br.com.douglas.mapper.destinatario.DestinatarioResponse;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import br.com.douglas.service.destinatario.DestinatarioService;
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
@RequestMapping("/api/destinatario")
public class DestinatarioController extends BaseController<Destinatario, DestinatarioRequest, DestinatarioResponse> {

    private final DestinatarioMapper destMapper;

    protected DestinatarioController(IBaseService<Destinatario> service, BaseMapper<Destinatario, DestinatarioRequest, DestinatarioResponse> responseMapper, DestinatarioMapper destMapper) {
        super(service, responseMapper);
        this.destMapper = destMapper;
    }

    @GetMapping("/find-list")
    @PageableAsQueryParam
    @Operation(description = "Consulta os dados e filtrando utilizando o padrão **RSQL** sem paginação")
    @Override
    public List<DestinatarioResponse> findList(@Parameter(description = BaseControllerConstants.FIND_PAGE_DOC)
                            @RequestParam(required = false) String search,
                            @SortDefault.SortDefaults({@SortDefault(sort = "id", direction = Sort.Direction.ASC)}) Sort sort) {
        if(Objects.nonNull(search) && !search.isBlank() && !search.isEmpty()) {
            return service.findAll(SpecificationUtils.rsqlToSpecification(search), sort).stream().map(mapper::toResponse).toList();
        }

        return ((DestinatarioService)getService()).findAllWithFunction(sort, destMapper);
    }
}
