package br.com.douglas.mapper.formapagamento;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormaPagamentoResponse {

    private String id;
    private String nome;
    private Boolean ativo;
}
