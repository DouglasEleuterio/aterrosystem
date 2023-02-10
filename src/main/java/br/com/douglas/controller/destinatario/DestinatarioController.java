package br.com.douglas.controller.destinatario;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Destinatario;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.destinatario.DestinatarioRequest;
import br.com.douglas.mapper.destinatario.DestinatarioResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/destinatario")
public class DestinatarioController extends BaseController<Destinatario, DestinatarioRequest, DestinatarioResponse> {
    protected DestinatarioController(IBaseService<Destinatario> service, BaseMapper<Destinatario, DestinatarioRequest, DestinatarioResponse> responseMapper) {
        super(service, responseMapper);
    }
}
