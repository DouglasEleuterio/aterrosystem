package br.com.douglas.controller.pagamento;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.pagamento.PagamentoRequest;
import br.com.douglas.mapper.pagamento.PagamentoResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController extends BaseController<Pagamento, PagamentoRequest, PagamentoResponse> {
    protected PagamentoController(IBaseService<Pagamento> service, BaseMapper<Pagamento, PagamentoRequest, PagamentoResponse> responseMapper) {
        super(service, responseMapper);
    }

    @GetMapping("/relatorio")
    public void gerarRelatorio(){

    }
}
