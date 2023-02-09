package br.com.douglas.controller.mapper.pagamento;

import br.com.douglas.controller.mapper.formapagamento.FormaPagamentoResponse;
import br.com.douglas.controller.mapper.instituicaobancaria.InstituicaoBancariaResponse;
import br.com.douglas.entity.entities.temp.CTR;
import br.com.douglas.entity.entities.temp.FormaPagamento;
import br.com.douglas.entity.entities.temp.InstituicaoBancaria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoResponse {

    private String id;
    private LocalDate dataPagamento;
    private Double valor;
    private FormaPagamentoResponse formaPagamento;
    private Boolean ativo;
    private CTR ctr;
    private InstituicaoBancariaResponse instituicaoBancaria;
}
