package br.com.douglas.mapper.relatorio;

import lombok.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioFinanceiroFilterRequest {
    private FilterRelatorioRequest search;
    private Integer page;
    private String sort;
}
/*
 {
	"search": {
		"ativo": null,
		"ctr": {
			"numero": null
		},
		"dataPagamentoAte": "2023-04-23T07:17:46.803Z",
		"dataPagamentoDe": "2023-04-23T07:17:46.803Z",
		"formaPagamento": {
			"id": ""
		},
		"instituicaoBancaria": [
			{
				"id": ""
			},
			{
				"id": "3c040f7c-dcbf-425a-b6a9-71a590bc2196"
			}
		],
		"origem": "TODOS"
	}
}
*/