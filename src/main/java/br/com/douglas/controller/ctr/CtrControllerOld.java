package br.com.douglas.controller.ctr;

import br.com.douglas.entity.entities.temp.CTR;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.service.ctr.CTRService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200/", "https://aterrosystem.com.br"})
@RestController
@RequestMapping("/api/ctr/old")
public class CtrControllerOld {

    private final CTRService entityService;

    public CtrControllerOld(CTRService service) {
        this.entityService = service;
    }

    @PostMapping
    public CTR create(@Valid @RequestBody CTR entity) throws DomainException {
//        return entityService.convert(entityService.save(entity));
        return entityService.save(entity);
    }

    @GetMapping(path = "/{id}")
    public CTR find(@PathVariable String id) throws DomainException {
        return entityService.findById(id);
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
        return entityService.findAll(paging, transportadoraId, numero, dataDe, dataAte);
    }
}
