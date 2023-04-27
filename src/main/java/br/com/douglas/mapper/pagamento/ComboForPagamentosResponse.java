package br.com.douglas.mapper.pagamento;

import br.com.douglas.mapper.IdAndNameResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ComboForPagamentosResponse {
    private String id;
    private IdAndNameResponse transportador;
}
