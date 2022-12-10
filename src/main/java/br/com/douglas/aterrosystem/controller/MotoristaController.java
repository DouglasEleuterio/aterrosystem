package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.CTR;
import br.com.douglas.aterrosystem.entity.Motorista;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.MotoristaService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/motorista")
public class MotoristaController {

    private final MotoristaService entityService;

    public MotoristaController(MotoristaService entityService) {
        this.entityService = entityService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public Motorista create(@Valid @RequestBody Motorista entity) throws DomainException {
        return entityService.save(entity);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
    public Iterable<Motorista> findAll (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "nome", direction = Sort.Direction.ASC) }
            ) Sort sort){
        return entityService.findAll(sort);
    }
}
