package br.com.douglas.controller.v2.relatorio;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.service.relatorio.RelatorioService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Relatorios V2")
@RequestMapping("/api/v2/relatorio")
@CrossOrigin(origins = {"http://localhost:4200/", "https://aterrosystem.com.br", "https://novo.aterrosystem.com.br"})
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/financeiro")
    public ResponseEntity<Resource> downloadRelatorioFinanceiro() throws DomainException {
        return relatorioService.gerarRelatorioPagamentos(null);
    }
}
