package com.bezkoder.spring.security.login;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class UtilsTest {
    public static void main(String[] args) {
        System.out.println(geraSegundaSemanaAtual());
        System.out.println(geraSegundaSemanaAnterior());
    }

    private static void obterSegundaFeiraSemanaAtual(){
        LocalDate hoje = LocalDate.now();
        LocalDate segundaFeira = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        System.out.println(segundaFeira);
    }

    private static void obterTercaFeiraSemanaAnterior(){
        LocalDate hoje = LocalDate.now();
        LocalDate segundaFeira = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate tercaFeira = segundaFeira.with(TemporalAdjusters.previousOrSame(DayOfWeek.TUESDAY));

        System.out.println(segundaFeira);
        System.out.println(tercaFeira);
    }

    private static LocalDate geraSegundaSemanaAtual(){
        return obterDataSemanaAtual(DayOfWeek.MONDAY);
    }
    private static LocalDate geraSegundaSemanaAnterior(){
        return obterDataSemanaAnterior(DayOfWeek.MONDAY);
    }

    private static LocalDate obterDataSemanaAtual(DayOfWeek day){
        LocalDate dataAtual = LocalDate.now();
        return dataAtual.with(TemporalAdjusters.previousOrSame(day));
    }

    private static LocalDate obterDataSemanaAnterior(DayOfWeek day){
        return obterDataSemanaAtual(DayOfWeek.MONDAY).with(TemporalAdjusters.previous(day));
    }

}
