package br.com.douglas.mapper.instituicaobancaria;

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
public class InstituicaoBancariaRequest {

    private String id;
    private String nome;
    private String agencia;
    private String conta;
    private Boolean ativo;
}
