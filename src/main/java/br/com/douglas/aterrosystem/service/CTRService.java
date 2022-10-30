package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.CTR;
import br.com.douglas.aterrosystem.entity.Pagamento;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.CTRRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class CTRService {

    private final CTRRepository repository;

    public CTRService(CTRRepository repository) {
        this.repository = repository;

    }

    @Transactional
    public CTR save(CTR ctr) throws DomainException {
        validate(ctr);
        return repository.save(ctr);
    }

    private void validate(CTR ctr) throws DomainException {
        if(Objects.isNull(ctr.getGerador())){
            throw new DomainException("Gerador Inválido");
        }
        if(Objects.isNull(ctr.getVeiculo())){
            throw new DomainException("Gerador Inválido");
        }
        if(Objects.isNull(ctr.getDestinatario())){
            throw new DomainException("Gerador Inválido");
        }
        if(Objects.isNull(ctr.getTransportador())){
            throw new DomainException("Gerador Inválido");
        }
        if(ctr.getPagamentos().isEmpty()){
            throw new DomainException("Necessita informar pagamento(s)");
        }
        for (Pagamento pagamento : ctr.getPagamentos()) {
            if(pagamento.getValor() < 0.01)
                throw new DomainException("Valor de pagamento inválido");
            if(Objects.isNull(pagamento.getFormaPagamento()))
                throw new DomainException("Forma de Pagamento inválido");
            if(Objects.isNull(pagamento.getDataPagamento()))
                throw new DomainException("Data de Pagamento inválido");
        }
    }
}
