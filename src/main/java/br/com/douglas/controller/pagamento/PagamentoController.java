package br.com.douglas.controller.pagamento;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.pagamento.PagamentoRequest;
import br.com.douglas.mapper.pagamento.PagamentoResponse;
import br.com.douglas.service.interfaces.IBaseService;
import br.com.douglas.service.pagamento.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController extends BaseController<Pagamento, PagamentoRequest, PagamentoResponse> {

    private final PagamentoService pagamentoService;
    protected PagamentoController(IBaseService<Pagamento> service, BaseMapper<Pagamento, PagamentoRequest, PagamentoResponse> responseMapper, PagamentoService pagamentoService) {
        super(service, responseMapper);
        this.pagamentoService = pagamentoService;
    }

    @GetMapping("/relatorio")
    public void gerarRelatorio(){

    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<?> informarPagamentoEmAberto(@PathVariable("id") String id,
                                                          @RequestBody @Valid PagamentoRequest request) throws DomainException {
//        pagamentoService.update(id, request);
        return ResponseEntity.accepted().body("Método não implementado");
    }
}
