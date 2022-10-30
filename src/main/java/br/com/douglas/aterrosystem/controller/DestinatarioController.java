package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.Destinatario;
import br.com.douglas.aterrosystem.entity.Gerador;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.DestinatarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/destinatario")
public class DestinatarioController {

    private final DestinatarioService entityService;

    public DestinatarioController(DestinatarioService entityService) {
        this.entityService = entityService;
    }


    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public Destinatario create(@Valid @RequestBody Destinatario entity) throws DomainException {
        return entityService.save(entity);
    }
}
