package br.com.douglas.service.relatorio.financeiro;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.exception.exceptions.ReportException;
import br.com.douglas.mapper.relatorio.InstituicaoBancariaFilterRequest;
import br.com.douglas.mapper.relatorio.RelatorioFinanceiroFilterRequest;
import br.com.douglas.service.relatorio.JasperReportService;
import br.com.douglas.service.relatorio.ReportData;
import br.com.douglas.service.relatorio.ReportType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioFinanceiroService extends JasperReportService {

    private final DataSource dataSource;
    public RelatorioFinanceiroService(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    public ResponseEntity<Resource> gerarRelatorioPagamentos(LocalDate de, LocalDate ate, RelatorioFinanceiroFilterRequest search) throws DomainException {
        Map<String, Object> paramsMap =
                constrirParametros(de, ate, search, SecurityContextHolder.getContext().getAuthentication().getName());
        return gerarRelatorioPdf("relatorio-financeiro-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss")), paramsMap).toResponseEntity();
    }

    private HashMap<String, Object> constrirParametros(LocalDate de, LocalDate ate, RelatorioFinanceiroFilterRequest search, String nomeUsuario) {
        var contasBancariasIdList = new ArrayList<String>();
        search.getSearch().getInstituicaoBancaria().forEach(ibc -> {
            contasBancariasIdList.add(ibc.getId());
        });
        construirNomeInstituicoesBancarias(search.getSearch().getInstituicaoBancaria());

        var params = new HashMap<String, Object>();
        params.put("de", de.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        params.put("ate", ate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        params.put("ibancarias", contasBancariasIdList);
        params.put("ibancariasNome", construirNomeInstituicoesBancarias(search.getSearch().getInstituicaoBancaria()));
        params.put("dataDe", de);
        params.put("dataAte", ate);
        params.put("usuario", nomeUsuario);
        params.put("localEData", "Aparecida de Goiânia, " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return params;
    }

    private String construirNomeInstituicoesBancarias(List<InstituicaoBancariaFilterRequest> instituicaoBancaria) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < instituicaoBancaria.size(); i++) {
            if(i == instituicaoBancaria.size() - 1 )
                sb.append(" ".concat(instituicaoBancaria.get(i).getNome()));
            else {
                sb.append(" ".concat(instituicaoBancaria.get(i).getNome().concat(",")));
            }
        }
        return sb.toString();
    }

    private ReportData gerarRelatorioPdf(String nomeRelatorio, Map<String, Object> paramsMap) throws ReportException {
        return generate(
                "aterrosystem-pagamentos.jasper",
                null,
                ReportType.PDF,
                nomeRelatorio,
                paramsMap);
    }
}
