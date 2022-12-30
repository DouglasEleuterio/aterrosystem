package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.CTR;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.CTRService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ctr")
public class CTRController {

    private final CTRService entityService;

    public CTRController(CTRService service) {
        this.entityService = service;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public CTR create(@Valid @RequestBody CTR entity) throws DomainException {
//        return entityService.convert(entityService.save(entity));
        return entityService.save(entity);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping(path = "/{id}")
    public CTR find(@PathVariable String id) throws DomainException {
        return entityService.findById(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
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
