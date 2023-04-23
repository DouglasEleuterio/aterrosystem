package br.com.douglas.mapper.relatorio;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterRelatorioRequest {
    private CTRFilterRequest ctr;
    private List<InstituicaoBancariaFilterRequest> instituicaoBancaria;
}
