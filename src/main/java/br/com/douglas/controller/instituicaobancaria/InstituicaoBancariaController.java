package br.com.douglas.controller.instituicaobancaria;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.InstituicaoBancaria;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.instituicaobancaria.InstituicaoBancariaRequest;
import br.com.douglas.mapper.instituicaobancaria.InstituicaoBancariaResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instituicao-bancaria")
public class InstituicaoBancariaController extends BaseController<InstituicaoBancaria, InstituicaoBancariaRequest, InstituicaoBancariaResponse> {
    protected InstituicaoBancariaController(IBaseService<InstituicaoBancaria> service, BaseMapper<InstituicaoBancaria, InstituicaoBancariaRequest, InstituicaoBancariaResponse> responseMapper) {
        super(service, responseMapper);
    }
}
