package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.AcumuladoMensalDto;
import br.com.douglas.aterrosystem.entity.AcumuladoSemanalDTO;
import br.com.douglas.aterrosystem.entity.CTR;
import br.com.douglas.aterrosystem.entity.RelacaoPagamentosDTO;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.DashBoardService;
import br.com.douglas.aterrosystem.service.FormaPagamentoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashBoardFinanceiro {

    private final DashBoardService dashBoardService;
    private final FormaPagamentoService formaPagamentoService;

    public DashBoardFinanceiro(DashBoardService dashBoardService, FormaPagamentoService formaPagamentoService) {
        this.dashBoardService = dashBoardService;
        this.formaPagamentoService = formaPagamentoService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/acumulado-mensal")
    public AcumuladoMensalDto getDashAcumuladoMensam (){
        return dashBoardService.acumuladoMensal();
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/acumulado-semanal")
    public AcumuladoSemanalDTO getDashAcumuladoSemanal(){
        return dashBoardService.acumuladoSemanal();
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/pagamentos-agrupados")
    public List<RelacaoPagamentosDTO> getDashPagamentosAgrupados(){
        return formaPagamentoService.getRelacaoPagamentos();
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/pagamentos-transportadora")
    public List<RelacaoPagamentosDTO> getRelacaoPagamentosTransportadora(){
        return formaPagamentoService.getRelacaoPagamentosTransportadora();
    }
}
