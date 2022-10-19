package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.Combo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.models.ComboResponse;
import br.com.douglas.aterrosystem.models.FormaPagamento;
import br.com.douglas.aterrosystem.service.FormaPagamentoService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/forma-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoService entityService;

    public FormaPagamentoController(FormaPagamentoService entityService) {
        this.entityService = entityService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
    public List<FormaPagamento> findAll (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "nome", direction = Sort.Direction.ASC) }
            ) Sort sort){
        List<FormaPagamento> result = new ArrayList<>();
        entityService.findAll(sort).forEach(entity -> result.add(entityService.convert(entity)));
        return result;
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
