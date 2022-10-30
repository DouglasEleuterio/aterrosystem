package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.Transportador;
import br.com.douglas.aterrosystem.entity.Veiculo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.VeiculoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/veiculo")
public class VeiculoController {

    private final VeiculoService entityService;

    public VeiculoController(VeiculoService entityService) {
        this.entityService = entityService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public Veiculo create(@Valid @RequestBody Veiculo entity) throws DomainException {
        return entityService.save(entity);
    }
}
