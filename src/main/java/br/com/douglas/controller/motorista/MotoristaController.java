package br.com.douglas.controller.motorista;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Motorista;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.motorista.MotoristaRequest;
import br.com.douglas.mapper.motorista.MotoristaResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/motorista")
public class MotoristaController extends BaseController<Motorista, MotoristaRequest, MotoristaResponse> {

    protected MotoristaController(IBaseService<Motorista> service, BaseMapper<Motorista, MotoristaRequest, MotoristaResponse> responseMapper) {
        super(service, responseMapper);
    }
}
