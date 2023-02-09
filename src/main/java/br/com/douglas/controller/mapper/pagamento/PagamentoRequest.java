package br.com.douglas.controller.mapper.pagamento;

import br.com.douglas.controller.mapper.formapagamento.FormaPagamentoRequest;
import br.com.douglas.controller.mapper.instituicaobancaria.InstituicaoBancariaRequest;
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
public class PagamentoRequest {

    private LocalDate dataPagamento;
    private Double valor;
    private FormaPagamentoRequest formaPagamento;
    private Boolean ativo;
    private CTR ctr;
    private InstituicaoBancariaRequest instituicaoBancaria;
}
