package br.com.douglas.controller.core;

import br.com.douglas.entity.base.BaseEntity;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import br.com.douglas.service.interfaces.IBaseService;
import br.com.douglas.service.interfaces.IbaseFindService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

public abstract class BaseRsqlFindController<T extends BaseEntity,R, P> {

    protected final IBaseService<T> service;
    protected final BaseMapper<T,R, P> mapper;

    protected BaseRsqlFindController(final IBaseService<T> service, final BaseMapper<T, R, P> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @PageableAsQueryParam
    @Operation(description = "Consulta os dados paginando e filtrando utilizando o padrão **RSQL**")
    public Page<P> findPage(@Parameter(description = BaseControllerConstants.FIND_PAGE_DOC)
                            @RequestParam(required = false) String search,
                            @SortDefault.SortDefaults({@SortDefault(sort = "id", direction = Sort.Direction.ASC)})
                            Pageable pageable) {
        if(Objects.nonNull(search) && !search.isBlank() && !search.isEmpty()) {
            return service.findAll(SpecificationUtils.rsqlToSpecification(search), pageable)
                    .map(mapper::toResponse);
        }
        return service.findAll(pageable).map(mapper::toResponse);
    }

    @GetMapping("/find-list")
    @PageableAsQueryParam
    @Operation(description = "Consulta os dados e filtrando utilizando o padrão **RSQL** sem paginação")
    public List<P> findList(@Parameter(description = BaseControllerConstants.FIND_PAGE_DOC)
                            @RequestParam(required = false) String search,
                            @SortDefault.SortDefaults({@SortDefault(sort = "id", direction = Sort.Direction.ASC)}) Sort sort) {
        if(Objects.nonNull(search) && !search.isBlank() && !search.isEmpty()) {
            return service.findAll(SpecificationUtils.rsqlToSpecification(search), sort).stream().map(mapper::toResponse).toList();
        }
        return service.findAll(sort).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/all")
    @Operation(description = "Consulta todos os dados sem paginação")
    public List<P> findAll(
            @SortDefault.SortDefaults({@SortDefault(sort = "id", direction = Sort.Direction.ASC)}) Sort sort) {
        return mapper.toResponseList(service.findAll(sort).stream().toList());
    }

    @GetMapping(path = "/{id}")
    @Operation(description = "Consulta o registro pelo ID")
    public P findById(@PathVariable String id) throws DomainException {
        return mapper.toResponse(service.findById(id));
    }

    protected IbaseFindService<T> getService() {
        return service;
    }
}
