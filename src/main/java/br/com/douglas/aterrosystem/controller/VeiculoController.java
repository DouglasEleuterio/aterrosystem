package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.Transportador;
import br.com.douglas.aterrosystem.entity.Veiculo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.VeiculoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
    public Page<Veiculo> findAll (
            @RequestParam(required = false, defaultValue = "") String modelo,
            @RequestParam(required = false, defaultValue = "") String placa,
            @RequestParam(required = false, defaultValue = "true") String ativo,
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "5") String size,
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "placa", direction = Sort.Direction.ASC) }
            ) Sort sort){
        Pageable paging = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), sort);
        paging.getSort().and(sort);
        return entityService.findAll(paging, placa, modelo, ativo);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws DomainException {
        entityService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
