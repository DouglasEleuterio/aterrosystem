package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.FormaPagamento;
import br.com.douglas.aterrosystem.entity.Pagamento;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {

    private final PagamentoService entityService;

    public PagamentoController(PagamentoService service) {
        this.entityService = service;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public Pagamento create(@Valid @RequestBody Pagamento entity) throws DomainException {
        return entityService.save(entity);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id) throws DomainException{
        entityService.alterarStatus(id);
        return ResponseEntity.accepted().build();
    }
}
