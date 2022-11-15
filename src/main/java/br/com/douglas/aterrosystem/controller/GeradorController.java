package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.FormaPagamento;
import br.com.douglas.aterrosystem.entity.Gerador;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.GeradorService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/gerador")
public class GeradorController {

    private final GeradorService entityService;

    public GeradorController(GeradorService entityService) {
        this.entityService = entityService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public Gerador create(@Valid @RequestBody Gerador entity) throws DomainException {
        return entityService.save(entity);
    }

    @GetMapping("/all")
    public List<Gerador> findAll (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "nome", direction = Sort.Direction.ASC) }
            ) Sort sort){
        List<Gerador> result = new ArrayList<>();
        return entityService.findAll(sort);
    }
}
