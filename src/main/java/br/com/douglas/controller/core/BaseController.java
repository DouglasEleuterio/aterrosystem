package br.com.douglas.controller.core;

import br.com.douglas.entity.base.BaseEntity;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.controller.mapper.BaseMapper;
import br.com.douglas.service.interfaces.IBaseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public class BaseController<T extends BaseEntity, R,P> extends BaseRsqlFindController<T,R,P> {

    protected BaseController(final IBaseService<T> service, BaseMapper<T,R,P> responseMapper) {
        super(service, responseMapper);
    }

    @PostMapping
    @Operation(description = "Cria um registro")
    public P create( @RequestBody R entityRequest) throws DomainException {
        return mapper.toResponse(getService().create(mapper.fromRequest(entityRequest)));
    }

    @PutMapping(path = "/{id}")
    @Operation(description = "Atualiza os dados de um registro")
    public P update(@PathVariable("id") String id, @Valid @RequestBody T entityRequest) throws DomainException {
        return mapper.toResponse(getService().update(id, entityRequest));
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Apaga o registro")
    public ResponseEntity<Void> delete(@PathVariable String id) throws DomainException {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    protected IBaseService<T> getService() {
        return (IBaseService<T>) super.getService();
    }
}
