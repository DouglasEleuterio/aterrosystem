package br.com.douglas.controller.combo;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Combo;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.combo.ComboRequest;
import br.com.douglas.mapper.combo.ComboResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/combo")
public class ComboController extends BaseController<Combo, ComboRequest, ComboResponse> {
    protected ComboController(IBaseService<Combo> service, BaseMapper<Combo, ComboRequest, ComboResponse> responseMapper) {
        super(service, responseMapper);
    }
}
