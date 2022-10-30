package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.Transportador;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.TransportadorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transportadora")
public class TransportadorController {

    private final TransportadorService entityService;

    public TransportadorController(TransportadorService entityService) {
        this.entityService = entityService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public Transportador create(@Valid @RequestBody Transportador entity) throws DomainException {
        return entityService.save(entity);
    }
}
