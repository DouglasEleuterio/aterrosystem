package br.com.douglas.controller.dashboard;

import br.com.douglas.entity.model.AcumuladoMensalDto;
import br.com.douglas.entity.model.AcumuladoSemanalDTO;
import br.com.douglas.entity.model.RelacaoPagamentosDTO;
import br.com.douglas.service.dashboard.DashBoardService;
import br.com.douglas.service.formapagamento.FormaPagamentoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:4200/", "https://aterrosystem.com.br"})
public class DashBoardFinanceiro {

    private final DashBoardService dashBoardService;
    private final FormaPagamentoService formaPagamentoService;

    public DashBoardFinanceiro(DashBoardService dashBoardService, FormaPagamentoService formaPagamentoService) {
        this.dashBoardService = dashBoardService;
        this.formaPagamentoService = formaPagamentoService;
    }

    @GetMapping("/acumulado-mensal")
    public AcumuladoMensalDto getDashAcumuladoMensam (){
        return dashBoardService.acumuladoMensal();
    }

    @GetMapping("/acumulado-semanal")
    public AcumuladoSemanalDTO getDashAcumuladoSemanal(){
        return dashBoardService.acumuladoSemanal();
    }

    @GetMapping("/pagamentos-agrupados")
    public List<RelacaoPagamentosDTO> getDashPagamentosAgrupados(){
        return formaPagamentoService.getRelacaoPagamentos();
    }

    @GetMapping("/pagamentos-transportadora")
    public List<RelacaoPagamentosDTO> getRelacaoPagamentosTransportadora(){
        return formaPagamentoService.getRelacaoPagamentosTransportadora();
    }
}
