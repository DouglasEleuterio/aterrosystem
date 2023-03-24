package br.com.douglas.mapper.pagamento;

import br.com.douglas.mapper.combo.ComboResponse;
import br.com.douglas.mapper.ctr.CtrResponse;
import br.com.douglas.mapper.formapagamento.FormaPagamentoResponse;
import br.com.douglas.mapper.instituicaobancaria.InstituicaoBancariaResponse;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoResponse {

    private String id;
    private LocalDate dataPagamento;
    private Double valor;
    private FormaPagamentoResponse formaPagamento;
    private Boolean ativo;
    private InstituicaoBancariaResponse instituicaoBancaria;
    private ComboResponse combo;
    private CtrResponse ctr;
}