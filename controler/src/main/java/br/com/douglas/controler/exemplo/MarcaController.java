package br.com.douglas.controler.exemplo;

import br.com.douglas.controler.core.BaseController;
import br.com.douglas.controler.mapper.mappers.marca.MarcaMapper;
import br.com.douglas.controler.mapper.mappers.marca.MarcaRequest;
import br.com.douglas.controler.mapper.mappers.marca.MarcaResponse;
import br.com.douglas.entity.entities.Marca;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/marca")
public class MarcaController extends BaseController<Marca, MarcaRequest, MarcaResponse> {
    protected MarcaController(final IBaseService<Marca> service, final MarcaMapper mapper) {
        super(service, mapper);
    }

}
