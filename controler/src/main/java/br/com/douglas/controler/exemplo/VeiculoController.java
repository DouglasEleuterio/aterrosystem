package br.com.douglas.controler.exemplo;

import br.com.douglas.controler.core.BaseController;
import br.com.douglas.controler.mapper.mappers.veiculo.VeiculoMapper;
import br.com.douglas.controler.mapper.mappers.veiculo.VeiculoRequest;
import br.com.douglas.controler.mapper.mappers.veiculo.VeiculoResponse;
import br.com.douglas.entity.entities.Veiculo;
import br.com.douglas.service.exemplo.IVeiculoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/veiculo")
public class VeiculoController extends BaseController<Veiculo, VeiculoRequest, VeiculoResponse> {

    public VeiculoController(final IVeiculoService veiculoService, final VeiculoMapper mapper) {
        super(veiculoService, mapper);
    }
}
