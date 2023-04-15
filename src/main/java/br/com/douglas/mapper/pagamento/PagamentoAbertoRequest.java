package br.com.douglas.mapper.pagamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoAbertoRequest {

    private Date data;
    private String formaPagamentoId;

}
