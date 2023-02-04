package br.com.douglas.entity.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelacaoPagamentosDTO {
    private String nome;
    private Double valor;
}
