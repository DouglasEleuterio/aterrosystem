package br.com.douglas.mapper.relatorio;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstituicaoBancariaFilterRequest {
    private String id;
    private String nome;
}
