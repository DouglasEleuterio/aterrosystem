package br.com.douglas.mapper.ctr;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoDescarteForInvoiceResponse {
    private String nome;
    private Double valor;

}
