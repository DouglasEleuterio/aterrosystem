package br.com.douglas.mapper.ctr;

import br.com.douglas.entity.entities.temp.Endereco;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DestinatarioForInvoiceResponse {
    private Endereco enderecoRecebimento;
    private String email;
}
