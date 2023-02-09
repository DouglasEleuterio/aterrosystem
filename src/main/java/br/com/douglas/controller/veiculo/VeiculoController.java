package br.com.douglas.controller.veiculo;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.veiculo.VeiculoRequest;
import br.com.douglas.mapper.veiculo.VeiculoResponse;
import br.com.douglas.entity.entities.temp.Veiculo;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/veiculo")
public class VeiculoController extends BaseController<Veiculo, VeiculoRequest, VeiculoResponse> {

    protected VeiculoController(IBaseService<Veiculo> service, BaseMapper<Veiculo, VeiculoRequest, VeiculoResponse> responseMapper) {
        super(service, responseMapper);
    }
}
