package br.com.douglas.service.pagamento;

import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.pagamento.PagamentoForTableResponse;
import br.com.douglas.mapper.pagamento.PagamentoRequest;
import br.com.douglas.repositories.pagamento.PagamentoRepository;
import br.com.douglas.service.formapagamento.FormaPagamentoService;
import br.com.douglas.service.impls.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

@Service
public class PagamentoService extends BaseService<Pagamento> {

    private final PagamentoRepository repository;
    private final FormaPagamentoService formaPagamentoService;

    public PagamentoService(PagamentoRepository repository, FormaPagamentoService formaPagamentoService) {
        super(repository);
        this.repository = repository;
        this.formaPagamentoService = formaPagamentoService;
    }

    public Pagamento save(Pagamento pagamento) throws DomainException {
        validate(pagamento);
        pagamento.setAtivo(true);
        return repository.save(pagamento);
    }


    @Override
    public void validate(Pagamento pagamento) throws DomainException {
        if (Objects.isNull(pagamento.getValor()) || pagamento.getValor() < 0.01 && !Objects.equals(pagamento.getFormaPagamento().getNome(), "Combo"))
            throw new DomainException("Valor inválido");
        if (!pagamento.getFormaPagamento().getNome().toLowerCase().contains("combo") && Objects.isNull(pagamento.getDataPagamento()) || (Objects.nonNull(pagamento.getDataPagamento()) && pagamento.getDataPagamento().isAfter(LocalDate.now()))) {
            throw new DomainException("Data de pagamento inválida");
        }
        if (Objects.isNull(pagamento.getFormaPagamento())) {
            throw new DomainException("Forma de pagamento inválida");
        }
    }

    public void alterarStatus(String id) throws DomainException {
        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new DomainException("Pagamento não encontrado!"));
        pagamento.setAtivo(!pagamento.getAtivo());
        repository.save(pagamento);
    }

    @Override
    public Page<Pagamento> findAll(Pageable pageable) {
        var all = repository.findAll(pageable);
        limparReferenciaCiclica(all);
        return all;
    }

    public Page<Pagamento> findAll(@Nullable Specification<Pagamento> specification, Pageable pageable){
        var all = repository.findAll(specification, pageable);
        limparReferenciaCiclica(all);
        return all;
    }

    private void limparReferenciaCiclica(Page<Pagamento> all) {
        all.getContent().forEach(pagamento1 -> {
                    //Limpa CTR
                    if (Objects.nonNull(pagamento1.getCtr()))
                        pagamento1.getCtr().setPagamentos(null);
                    //Limpa Combo
                    else if (Objects.nonNull(pagamento1.getCombo()))
                        pagamento1.getCombo().setPagamentos(null);
                }
        );
    }

    public Pagamento update(String id, PagamentoRequest entity) throws DomainException {
        var pgto = this.findById(id);
        var formaPagamento = formaPagamentoService.findById(entity.getFormaPagamento().getId());
        pgto.setDataPagamento(entity.getDataPagamento());
        pgto.setFormaPagamento(formaPagamento);
        return repository.save(pgto);
    }

    @Transactional(readOnly = true)
    public Page<PagamentoForTableResponse> findAllByTable(Specification<Pagamento> spec, Pageable pageable, Function<Pagamento, PagamentoForTableResponse> converter) {
        return repository.findAll(spec, pageable).map(converter);
    }
}
