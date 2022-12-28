package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.entity.FormaPagamento;
import br.com.douglas.aterrosystem.service.FormaPagamentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/forma-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoService entityService;

    public FormaPagamentoController(FormaPagamentoService entityService) {
        this.entityService = entityService;
    }

//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
    public Page<FormaPagamento> findAll (
            @RequestParam(required = false, defaultValue = "") String nome,
            @RequestParam(required = false, defaultValue = "true") String ativo,
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "5") String size,
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "nome", direction = Sort.Direction.ASC) }
            ) Sort sort){
        Pageable paging = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), sort);
        paging.getSort().and(sort);
        return entityService.findAll(paging, nome, ativo);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public FormaPagamento create(@Valid @RequestBody FormaPagamento entity) throws DomainException {
        return entityService.convert(entityService.save(entity));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id){
        entityService.alterarStatus(id);
        return ResponseEntity.accepted().build();
    }
}
