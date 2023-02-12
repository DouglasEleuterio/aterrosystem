package br.com.douglas.controller.combo;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.controller.core.BaseControllerConstants;
import br.com.douglas.entity.entities.temp.Combo;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.combo.ComboRequest;
import br.com.douglas.mapper.combo.ComboResponse;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import br.com.douglas.service.combo.ComboService;
import br.com.douglas.service.interfaces.IBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/combo")
public class ComboController extends BaseController<Combo, ComboRequest, ComboResponse> {

    private final ComboService comboService;

    protected ComboController(IBaseService<Combo> service, BaseMapper<Combo, ComboRequest, ComboResponse> responseMapper, ComboService comboService) {
        super(service, responseMapper);
        this.comboService = comboService;
    }

    @GetMapping
    @PageableAsQueryParam
    @Operation(description = "Consulta os dados paginando e filtrando utilizando o padrão **RSQL**")
    public Page<ComboResponse> findPage(@Parameter(description = BaseControllerConstants.FIND_PAGE_DOC)
                            @RequestParam(required = false) String search,
                            @SortDefault.SortDefaults({@SortDefault(sort = "id", direction = Sort.Direction.ASC)})
                            Pageable pageable) {
        if(Objects.nonNull(search) && !search.isBlank() && !search.isEmpty()) {
            return comboService.findAll(SpecificationUtils.rsqlToSpecification(search), pageable)
                    .map(mapper::toResponse);
        }
        return comboService.findPageAll(pageable);
    }
}
