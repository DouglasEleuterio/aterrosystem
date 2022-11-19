package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.AcumuladoMensalDto;
import br.com.douglas.aterrosystem.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DashBoardService {

    private final PagamentoRepository pagamentoRepository;
    private String ANO_ATUAL = String.valueOf(Year.now().getValue());
    private String ANO_ANTERIOR = String.valueOf(Year.now().minusYears(1L).getValue());
    private String INICIO_MES = "1";

    public DashBoardService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public AcumuladoMensalDto acumuladoMensal() {
        return AcumuladoMensalDto.builder()
                .anoAtual(ANO_ATUAL)
                .anoAnterior(ANO_ANTERIOR)
                .dataAnoAtual(getTotaisPagamentoAno(ANO_ATUAL))
                .dataAnoAnterior(getTotaisPagamentoAno(ANO_ANTERIOR))
                .build();
    }

    private List<Integer> getTotaisPagamentoAno(String ano){
        List<Integer> valores = new ArrayList<>();
        valores.add(geraJaneiro(ano));
        valores.add(geraFevereiro(ano));
        valores.add(geraMarco(ano));
        valores.add(geraAbril(ano));
        valores.add(geraMaio(ano));
        valores.add(geraJunho(ano));
        valores.add(geraJulho(ano));
        valores.add(geraAgosto(ano));
        valores.add(geraSetembro(ano));
        valores.add(geraOutubro(ano));
        valores.add(geraNovembro(ano));
        valores.add(geraDezembro(ano));
        return valores;
    }

    private Integer geraDezembro(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.DECEMBER + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.DECEMBER + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraNovembro(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.NOVEMBER + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.NOVEMBER + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraOutubro(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.OCTOBER + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.OCTOBER + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraSetembro(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.SEPTEMBER + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.SEPTEMBER + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraAgosto(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.AUGUST + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.AUGUST + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraJulho(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.JULY + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.JULY + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraJunho(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.JUNE + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.JUNE + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraMaio(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.MAY + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.MAY + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraAbril(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.APRIL + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.APRIL + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraMarco(String ano) {
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(
                getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.MARCH + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.MARCH + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraFevereiro(String ano){
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.FEBRUARY + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.FEBRUARY + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private Integer geraJaneiro(String ano){
        AtomicReference<Integer> value = new AtomicReference<>(0);
        pagamentoRepository.somaMensal(getPrimeiroDiaMes(Integer.parseInt(ano), Calendar.JANUARY + 1), getUltimoDiaMes(Integer.parseInt(ano), Calendar.JANUARY + 1)
        ).ifPresent(value::set);
        return value.get();
    }

    private LocalDate getUltimoDiaMes(Integer ano, Integer mes){
        LocalDate inicioMes = LocalDate.of(ano, mes, 1);
        int ultimoDiaMes = inicioMes.with(TemporalAdjusters.lastDayOfMonth()).get(ChronoField.DAY_OF_MONTH);
        return LocalDate.of(ano, mes, ultimoDiaMes);
    }
    private LocalDate getPrimeiroDiaMes(Integer ano, Integer mes){
        return LocalDate.of(ano, mes,1);
    }
}

