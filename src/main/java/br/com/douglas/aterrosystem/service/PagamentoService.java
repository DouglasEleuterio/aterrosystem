package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Pagamento;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public Pagamento save(Pagamento pagamento) throws DomainException{
        validate(pagamento);
        pagamento.setAtivo(true);
        return repository.save(pagamento);
    }


    private void validate(Pagamento pagamento) throws DomainException{
        if(Objects.isNull(pagamento.getValor()) || pagamento.getValor() < 0.01)
            throw new DomainException("Valor inválido");
        if(Objects.isNull(pagamento.getDataPagamento()) || pagamento.getDataPagamento().isAfter(LocalDate.now())){
            throw new DomainException("Data de pagamento inválido");
        }if(Objects.isNull(pagamento.getFormaPagamento())){
            throw new DomainException("Forma de pagamento inválida");
        }
    }

    public void alterarStatus(Long id) throws DomainException {
        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new DomainException("Pagamento não encontrado!"));
        pagamento.setAtivo(!pagamento.isAtivo());
        repository.save(pagamento);
    }
}
