package br.com.douglas.aterrosystem.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcumuladoSemanalDTO {
    private List<Integer> semanaAtual;
    private List<Integer> semanaPassada;
}

