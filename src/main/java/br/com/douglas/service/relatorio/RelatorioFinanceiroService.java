package br.com.douglas.service.relatorio;

import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.exception.exceptions.ReportException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public ResponseEntity<Resource> gerarRelatorioPagamentos(List<Pagamento> pagamentos) throws DomainException {
        Map<String, Object> paramsMap =
                constrirParametros(pagamentos, SecurityContextHolder.getContext().getAuthentication().getName());
        return
                gerarRelatorioPdf("relatorio-financeiro-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss")), paramsMap).toResponseEntity();
    }

    private HashMap<String, Object> constrirParametros(List<Pagamento> pagamentos, String nomeUsuario) {
        var params = new HashMap<String, Object>();
        params.put("pagamentos", pagamentos);
        params.put("usuário", nomeUsuario);
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
