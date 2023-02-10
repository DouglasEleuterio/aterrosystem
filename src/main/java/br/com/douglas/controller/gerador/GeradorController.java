package br.com.douglas.controller.gerador;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Gerador;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.gerador.GeradorRequest;
import br.com.douglas.mapper.gerador.GeradorResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gerador")
public class GeradorController extends BaseController<Gerador, GeradorRequest, GeradorResponse> {

    protected GeradorController(IBaseService<Gerador> service, BaseMapper<Gerador, GeradorRequest, GeradorResponse> responseMapper) {
        super(service, responseMapper);
    }
}
