package br.com.douglas.mapper.aquisicao;

import br.com.douglas.mapper.combo.ComboAquisicaoRequest;
import br.com.douglas.model.entity.BaseEntityRequest;
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
public class AquisicaoRequest {

    private ComboAquisicaoRequest combo;
    private BaseEntityRequest formaPagamento;
    private Integer quantidadeAdquirida;
    private LocalDate dataPagamento;
    private Double valorPago;
    private Double desconto;
    private Boolean ativo;
}
