package br.com.douglas.mapper.aquisicao;

import br.com.douglas.mapper.combo.ComboResponse;
import br.com.douglas.mapper.formapagamento.FormaPagamentoResponse;
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
public class AquisicaoResponse {

    private String id;
    private ComboResponse combo;
    private FormaPagamentoResponse formaPagamento;
    private Integer quantidadeAdquirida;
    private LocalDate dataPagamento;
    private Double valorPago;
    private Double desconto;
    private Boolean ativo;
}
