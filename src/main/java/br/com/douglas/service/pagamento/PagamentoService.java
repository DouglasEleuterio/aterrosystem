package br.com.douglas.service.pagamento;

import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.repositories.pagamento.PagamentoRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class PagamentoService extends BaseService<Pagamento> {

    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Pagamento save(Pagamento pagamento) throws DomainException {
        validate(pagamento);
        pagamento.setAtivo(true);
        return repository.save(pagamento);
    }


    @Override
    public void validate(Pagamento pagamento) throws DomainException{
        if(Objects.isNull(pagamento.getValor()) || pagamento.getValor() < 0.01 && !Objects.equals(pagamento.getFormaPagamento().getNome(), "Combo"))
            throw new DomainException("Valor inválido");
        if(Objects.isNull(pagamento.getDataPagamento()) || (pagamento.getDataPagamento().isAfter(LocalDate.now()))){
            throw new DomainException("Data de pagamento inválida");
        }if(Objects.isNull(pagamento.getFormaPagamento())){
            throw new DomainException("Forma de pagamento inválida");
        }
    }

    public void alterarStatus(String id) throws DomainException {
        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new DomainException("Pagamento não encontrado!"));
        pagamento.setAtivo(!pagamento.getAtivo());
        repository.save(pagamento);
    }
}
