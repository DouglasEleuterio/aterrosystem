package br.com.douglas.service.relatorio;

import br.com.douglas.exception.exceptions.ReportException;

import java.util.List;
import java.util.Map;

public interface IReportService {
    <T> ReportData generate(final String jasperFile,
                            final List<T> dataset,
                            final ReportType reportType,
                            final String reportFileName,
                            final Map<String, Object> parameters) throws ReportException;
}
