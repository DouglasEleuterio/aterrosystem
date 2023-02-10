package br.com.douglas.mapper.pagamento;

import br.com.douglas.mapper.formapagamento.FormaPagamentoResponse;
import br.com.douglas.mapper.instituicaobancaria.InstituicaoBancariaResponse;
import br.com.douglas.entity.entities.temp.CTR;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private CTR ctr;
    private InstituicaoBancariaResponse instituicaoBancaria;
}
