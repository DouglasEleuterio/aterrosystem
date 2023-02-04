package br.com.douglas.controler.exemplo;

import br.com.douglas.controler.core.BaseController;
import br.com.douglas.controler.mapper.mappers.modelo.ModeloMapper;
import br.com.douglas.controler.mapper.mappers.modelo.ModeloRequest;
import br.com.douglas.controler.mapper.mappers.modelo.ModeloResponse;
import br.com.douglas.entity.entities.Modelo;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/modelo")
public class ModeloController extends BaseController<Modelo, ModeloRequest, ModeloResponse> {

    protected ModeloController(IBaseService<Modelo> service, ModeloMapper mapper) {
        super(service, mapper);
    }
}
