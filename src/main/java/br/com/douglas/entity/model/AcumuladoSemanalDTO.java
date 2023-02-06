package br.com.douglas.entity.model;

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

