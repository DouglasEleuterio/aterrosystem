package br.com.douglas.controller.mapper.aquisicao;

import br.com.douglas.entity.entities.temp.Combo;
import br.com.douglas.entity.entities.temp.FormaPagamento;
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
    private Combo combo;
    private FormaPagamento formaPagamento;
    private Integer quantidadeAdquirida;
    private LocalDate dataPagamento;
    private Double valorPago;
    private Double desconto;
    private Boolean ativo;
}
