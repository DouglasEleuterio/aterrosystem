package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.TipoDescarte;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.models.TipoDescarteResponse;
import br.com.douglas.aterrosystem.service.TipoDescarteService;
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
@RequestMapping("/api/tipo-descarte")
public class TipoDescarteController {

    private final TipoDescarteService entityService;


    public TipoDescarteController(TipoDescarteService tipoDescarteService) {
        this.entityService = tipoDescarteService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
    public Page<TipoDescarte> findAll (
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
    @GetMapping("/all-ativo")
    public List<TipoDescarteResponse> findAllAtivo (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "nome", direction = Sort.Direction.ASC) }
            ) Sort sort){
        List<TipoDescarteResponse> result = new ArrayList<>();
        entityService.findAllAtivo(sort).forEach(tipoDescarte -> result.add(convert(tipoDescarte)));
        return result;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public TipoDescarteResponse create(@Valid @RequestBody TipoDescarte entity) throws DomainException {
        return convert(entityService.save(entity));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping
    public TipoDescarteResponse update(@Valid @RequestBody TipoDescarte entity) throws DomainException {
        return convert(entityService.update(entity));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws DomainException {
        entityService.delete(id);
        return ResponseEntity.accepted().build();
    }

    private TipoDescarteResponse convert(TipoDescarte entity){
        return TipoDescarteResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .valor(entity.getValor())
                .ativo(entity.getAtivo())
                .build();
    }
}
