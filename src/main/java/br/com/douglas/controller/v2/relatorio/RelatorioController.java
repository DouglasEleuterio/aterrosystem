package br.com.douglas.controller.v2.relatorio;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.relatorio.RelatorioFinanceiroFilterRequest;
import br.com.douglas.service.relatorio.RelatorioFinanceiroService;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController(value = "Relatorios V2")
@RequestMapping("/api/v2/relatorio")
@CrossOrigin(origins = {"http://localhost:4200/", "https://aterrosystem.com.br", "https://novo.aterrosystem.com.br"})
public class RelatorioController {

    private final RelatorioFinanceiroService relatorioFinanceiroService;

    public RelatorioController(RelatorioFinanceiroService relatorioFinanceiroService) {
        this.relatorioFinanceiroService = relatorioFinanceiroService;
    }

    @GetMapping("/financeiro")
    public ResponseEntity<Resource> downloadRelatorioFinanceiro(@RequestParam("dataDe") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDe,
                                                                @RequestParam("dataAte") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAte) throws DomainException {
        return relatorioFinanceiroService.gerarRelatorioPagamentos(dataDe, dataAte, null);
    }

    @PostMapping("/financeiro")
    public ResponseEntity<Resource> downloadRelatorioFinanceiroPost(@RequestParam("dataDe") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDe,
                                                                    @RequestParam("dataAte") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAte,
                                                                    @RequestBody(required = true) RelatorioFinanceiroFilterRequest search) throws DomainException {
        return relatorioFinanceiroService.gerarRelatorioPagamentos(dataDe, dataAte, search);
    }
}
