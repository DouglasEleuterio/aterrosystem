package br.com.douglas.controller.formapagamento;


import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.FormaPagamento;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.formapagamento.FormaPagamentoRequest;
import br.com.douglas.mapper.formapagamento.FormaPagamentoResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/formaPagamento")
public class FormaPagamentoController extends BaseController<FormaPagamento, FormaPagamentoRequest, FormaPagamentoResponse> {
    protected FormaPagamentoController(IBaseService<FormaPagamento> service, BaseMapper<FormaPagamento, FormaPagamentoRequest, FormaPagamentoResponse> responseMapper) {
        super(service, responseMapper);
    }
}
