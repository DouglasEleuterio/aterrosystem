package br.com.douglas.service.relatorio.combo;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.exception.exceptions.ReportException;
import br.com.douglas.service.relatorio.JasperReportService;
import br.com.douglas.service.relatorio.ReportData;
import br.com.douglas.service.relatorio.ReportType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioComboService extends JasperReportService {

    private final DataSource dataSource;
    public RelatorioComboService(DataSource dataSource, DataSource dataSource1) {
        super(dataSource);
        this.dataSource = dataSource1;
    }

    public ResponseEntity<Resource> gerarRelatorioSaldoCombo() throws DomainException {
        return gerarRelatorioPdf("relatorio-saldo_combo-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss")), constrirParametros()).toResponseEntity();
    }

    private HashMap<String, Object> constrirParametros() {
        return new HashMap<String, Object>();
    }
    private ReportData gerarRelatorioPdf(String nomeRelatorio, Map<String, Object> paramsMap) throws ReportException {
        return generate(
                "relatorio-saldo-combo.jasper",
                null,
                ReportType.PDF,
                nomeRelatorio,
                paramsMap);
    }

}
