package br.com.douglas.controller.transportador;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.transportador.TransportadorRequest;
import br.com.douglas.mapper.transportador.TransportadorResponse;
import br.com.douglas.entity.entities.temp.Transportador;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transportador")
public class TransportadorController extends BaseController<Transportador, TransportadorRequest, TransportadorResponse> {
    protected TransportadorController(IBaseService<Transportador> service, BaseMapper<Transportador, TransportadorRequest, TransportadorResponse> responseMapper) {
        super(service, responseMapper);
    }
}
