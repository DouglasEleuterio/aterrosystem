package br.com.douglas.controller.mapper.tipodescarte;

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
public class TipoDescarteResponse {

    private String id;
    private String nome;
    private Double valor;
    private Boolean ativo;
}
