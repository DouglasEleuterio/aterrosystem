package br.com.douglas.controller.v1.pagamento;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.pagamento.PagamentoForTableResponse;
import br.com.douglas.mapper.pagamento.PagamentoMapper;
import br.com.douglas.mapper.pagamento.PagamentoRequest;
import br.com.douglas.mapper.pagamento.PagamentoResponse;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import br.com.douglas.service.interfaces.IBaseService;
import br.com.douglas.service.pagamento.PagamentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController extends BaseController<Pagamento, PagamentoRequest, PagamentoResponse> {

    private final PagamentoService pagamentoService;
    private final PagamentoMapper pagamentoMapper;
    protected PagamentoController(IBaseService<Pagamento> service, BaseMapper<Pagamento, PagamentoRequest, PagamentoResponse> responseMapper, PagamentoService pagamentoService, PagamentoMapper pagamentoMapper) {
        super(service, responseMapper);
        this.pagamentoService = pagamentoService;
        this.pagamentoMapper = pagamentoMapper;
    }

    @GetMapping("/v2/find-list")
    public Page<PagamentoForTableResponse> getAll(@RequestParam(required = false) String search,
                                                  @SortDefault.SortDefaults({@SortDefault(sort = "id", direction = Sort.Direction.ASC)}) Pageable pageable){
        if (Objects.nonNull(search) && !search.isBlank() && !search.isEmpty()){
            return pagamentoService.findAllByTable(SpecificationUtils.rsqlToSpecification(search), pageable, pagamentoMapper::forTableResponse);
        } else {
            return service.findAll(pageable).map(pagamentoMapper::forTableResponse);
        }
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<?> informarPagamentoEmAberto(@PathVariable("id") String id,
                                                          @RequestBody @Valid PagamentoRequest request) throws DomainException {
        var atualizado = pagamentoService.update(id, request);
        return ResponseEntity.accepted().body(atualizado);
    }
}
