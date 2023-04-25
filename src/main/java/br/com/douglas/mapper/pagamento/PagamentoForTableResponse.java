package br.com.douglas.mapper.pagamento;

import br.com.douglas.mapper.CTRIdAndNumeroResponse;
import br.com.douglas.mapper.IdAndNameResponse;
import br.com.douglas.model.entity.BaseEntityResponse;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoForTableResponse {
    private LocalDate dataPagamento;
    private Double valor;
    private IdAndNameResponse formaPagamento;
    private Boolean ativo;
    private CTRIdAndNumeroResponse ctr;
    private BaseEntityResponse combo;
    private IdAndNameResponse instituicaoBancaria;
}