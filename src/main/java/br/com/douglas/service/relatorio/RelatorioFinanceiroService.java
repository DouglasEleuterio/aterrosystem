package br.com.douglas.service.relatorio;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.exception.exceptions.ReportException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioFinanceiroService extends JasperReportService {

    private final DataSource dataSource;
    public RelatorioFinanceiroService(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    public ResponseEntity<Resource> gerarRelatorioPagamentos(LocalDate de, LocalDate ate) throws DomainException {
        Map<String, Object> paramsMap =
                constrirParametros(de, ate, SecurityContextHolder.getContext().getAuthentication().getName());
        return gerarRelatorioPdf("relatorio-financeiro-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss")), paramsMap).toResponseEntity();
    }

    private HashMap<String, Object> constrirParametros(LocalDate de, LocalDate ate, String nomeUsuario) {
        var params = new HashMap<String, Object>();
//        params.put("periodo", "de: " + de.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " ate: " + ate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        params.put("de", de);
        params.put("ate", ate);
        params.put("dataDe", de);
        params.put("dataAte", ate);
        params.put("usuario", nomeUsuario);
        params.put("localEData", "Aparecida de Goiânia, " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy.")));
        return params;
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
