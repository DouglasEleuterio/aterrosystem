package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.Aquisicao;
import br.com.douglas.aterrosystem.entity.CTR;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.AquisicaoService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/aquisicao")
public class AquisicaoController {

    private final AquisicaoService entityService;

    public AquisicaoController(AquisicaoService entityService) {
        this.entityService = entityService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
    public Iterable<Aquisicao> findAll (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "dataPagamento", direction = Sort.Direction.DESC) }
            ) Sort sort){
        return entityService.findAll(sort);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public Aquisicao create(@Valid @RequestBody Aquisicao entity) throws DomainException {
        return entityService.save(entity);
    }
}
