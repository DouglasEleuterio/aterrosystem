package br.com.douglas.mapper.combo;

import br.com.douglas.mapper.tipodescarte.TipoDescarteResponse;
import br.com.douglas.mapper.transportador.TransportadorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboResponse {

    private String id;
    private TransportadorResponse transportador;
    private TipoDescarteResponse tipoDescarte;
    private Integer saldo;
    private Boolean ativo;
    private LocalDate dataPagamento;
}
