package br.com.douglas.controller.aquisicao;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Aquisicao;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.aquisicao.AquisicaoRequest;
import br.com.douglas.mapper.aquisicao.AquisicaoResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aquisicao")
public class AquisicaoController extends BaseController<Aquisicao, AquisicaoRequest, AquisicaoResponse> {

    protected AquisicaoController(IBaseService<Aquisicao> service, BaseMapper<Aquisicao, AquisicaoRequest, AquisicaoResponse> responseMapper) {
        super(service, responseMapper);
    }
}
