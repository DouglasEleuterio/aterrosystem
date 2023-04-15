package br.com.douglas.controller.v1.ctr;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.CTR;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.ctr.CtrRequest;
import br.com.douglas.mapper.ctr.CtrResponse;
import br.com.douglas.service.ctr.CTRService;
import br.com.douglas.service.interfaces.IBaseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ctr")
public class CtrController extends BaseController<CTR, CtrRequest, CtrResponse> {

    private final CTRService ctrService;

    protected CtrController(IBaseService<CTR> service, BaseMapper<CTR, CtrRequest, CtrResponse> responseMapper,
                            CTRService ctrService) {
        super(service, responseMapper);
        this.ctrService = ctrService;
    }

    @PostMapping("/save")
    @Operation(description = "Cria um registro")
    public ResponseEntity<Void> save(@RequestBody CtrRequest entityRequest) throws DomainException {
        ctrService.save(mapper.fromRequest(entityRequest));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/all-old")
    public Page<CTR> findAll (
            @RequestParam(required = false) String transportadoraId,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) String dataDe,
            @RequestParam(required = false) String dataAte,
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "5") String size,
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "geracao", direction = Sort.Direction.DESC) }
            ) Sort sort){
        Pageable paging = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), sort);
        paging.getSort().and(sort);
        return ctrService.findAll(paging, transportadoraId, numero, dataDe, dataAte);
    }
}
