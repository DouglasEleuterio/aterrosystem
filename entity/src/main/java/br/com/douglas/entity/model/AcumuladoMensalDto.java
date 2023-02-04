package br.com.douglas.entity.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcumuladoMensalDto {
    private String anoAtual;
    private String anoAnterior;
    private List<Integer> dataAnoAtual;
    private List<Integer> dataAnoAnterior;
}
