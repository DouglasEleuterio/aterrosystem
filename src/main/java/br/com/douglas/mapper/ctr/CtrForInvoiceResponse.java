package br.com.douglas.mapper.ctr;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CtrForInvoiceResponse {
    private Integer numero;
    private LocalDateTime geracao;
    private DestinatarioForInvoiceResponse destinatario;

    private TransportadorForInvoiceResponse transportador;

    private VeiculoForInvoiceResponse veiculo;
    private MotoristaForInvoiceResponse motorista;

    private List<TipoDescarteForInvoiceResponse> tipoDescartes;

    private List<PagamentoForInvoiceResponse> pagamentos;

}
