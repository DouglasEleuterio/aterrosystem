package br.com.douglas.service.relatorio;

import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.exception.exceptions.ReportException;
import br.com.douglas.message.messages.Message;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService extends JasperReportService {

    public ReportData gerarRelatorioPagamentos(List<Pagamento> pagamentos) throws DomainException {
        Map<String, Object> paramsMap =
                constrirParametros(pagamentos, SecurityContextHolder.getContext().getAuthentication().getName());
        return
                gerarRelatorioPdf("relatorio-financeiro" + LocalDateTime.now(), paramsMap);
    }


    private HashMap<String, Object> constrirParametros(List<Pagamento> pagamentos, String nomeUsuario) {
        var params = new HashMap<String, Object>();
        params.put("footer", Message.toLocale("relatorio.rodape"));
        params.put("pagamentos", pagamentos);
        params.put("usuário", nomeUsuario);
        params.put("localEData", "Aparecida de Goiânia, " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy.")));
        return params;
    }

    private ReportData gerarRelatorioPdf(String nomeRelatorio, Map<String, Object> paramsMap) throws ReportException {
        return generate(
                "relatorio-pagamentos",
                null,
                ReportType.PDF,
                nomeRelatorio,
                paramsMap);
    }
}
