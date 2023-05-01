package br.com.douglas.mapper.ctr;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoForInvoiceResponse {
    private Double valor;
    private LocalDate dataPagamento;
    private FormaPagamentoForInvoice formaPagamento;

}
