package br.com.douglas.controller.aquisicao;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Aquisicao;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.aquisicao.AquisicaoRequest;
import br.com.douglas.mapper.aquisicao.AquisicaoResponse;
import br.com.douglas.service.aquisicao.AquisicaoService;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aquisicao")
public class AquisicaoController extends BaseController<Aquisicao, AquisicaoRequest, AquisicaoResponse> {

    protected AquisicaoController(IBaseService<Aquisicao> service, BaseMapper<Aquisicao, AquisicaoRequest, AquisicaoResponse> responseMapper) {
        super(service, responseMapper);
    }

    @GetMapping(value = "/combo/{id}")
    public Aquisicao getAquisicaoByCombo(@PathVariable String id) throws DomainException {
        return ((AquisicaoService) getService()).findByComboId(id);
    }
}
