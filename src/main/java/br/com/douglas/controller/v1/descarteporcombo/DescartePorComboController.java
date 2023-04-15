package br.com.douglas.controller.v1.descarteporcombo;

import br.com.douglas.entity.entities.temp.DescartePorCombo;
import br.com.douglas.service.descartepocombo.DescartePorComboService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
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

    @GetMapping("/combo-id/{comboId}")
    public List<DescartePorCombo> findByComboId (
            @SortDefault.SortDefaults(
                    { @SortDefault(sort = "ctr.geracao", direction = Sort.Direction.DESC) }
            ) Sort sort, @PathVariable String comboId) {
        return entityService.findAllByCombo(comboId, sort);
    }
}
