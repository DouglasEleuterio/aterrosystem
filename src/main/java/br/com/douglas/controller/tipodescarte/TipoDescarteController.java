package br.com.douglas.controller.tipodescarte;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.TipoDescarte;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.tipodescarte.TipoDescarteRequest;
import br.com.douglas.mapper.tipodescarte.TipoDescarteResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipo-descarte")
public class TipoDescarteController extends BaseController<TipoDescarte, TipoDescarteRequest, TipoDescarteResponse> {

    protected TipoDescarteController(IBaseService<TipoDescarte> service, BaseMapper<TipoDescarte, TipoDescarteRequest, TipoDescarteResponse> responseMapper) {
        super(service, responseMapper);
    }
}
