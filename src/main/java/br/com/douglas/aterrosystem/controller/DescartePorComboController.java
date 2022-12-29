package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.DescartePorCombo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.DescartePorComboService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/descarte-por-combo")
public class DescartePorComboController {

    private final DescartePorComboService entityService;

    public DescartePorComboController(DescartePorComboService entityService) {
        this.entityService = entityService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/combo-id/{comboId}")
    public List<DescartePorCombo> findByComboId (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "id", direction = Sort.Direction.DESC) }
            ) Sort sort, @PathVariable String comboId) throws DomainException {
        return entityService.findAllByCombo(comboId);
    }
}
