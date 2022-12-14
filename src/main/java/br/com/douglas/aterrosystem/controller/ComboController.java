package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.Combo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.models.ComboResponse;
import br.com.douglas.aterrosystem.service.ComboService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/combo")
public class ComboController{

    private final ComboService entityService;

    public ComboController(ComboService comboService) {
        this.entityService = comboService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Combo> findAll (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "saldo", direction = Sort.Direction.ASC) }
            ) Sort sort){
        return entityService.findAll(sort);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/all/{transportadora}")
    public List<Combo> findAllByTransportadora (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "saldo", direction = Sort.Direction.ASC) }
            ) Sort sort, @PathVariable String transportadora){
        return entityService.findAllByTransportadoraId(transportadora, sort);
    }
}
