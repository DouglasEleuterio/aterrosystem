package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.CTR;
import br.com.douglas.aterrosystem.entity.Destinatario;
import br.com.douglas.aterrosystem.entity.FormaPagamento;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.models.TipoDescarteResponse;
import br.com.douglas.aterrosystem.service.CTRService;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public Iterable<CTR> findAll (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "geracao", direction = Sort.Direction.DESC) }
            ) Sort sort){
        return entityService.findAll(sort);
    }
}
