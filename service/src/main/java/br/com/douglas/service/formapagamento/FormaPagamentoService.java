package br.com.douglas.service.formapagamento;


import br.com.douglas.entity.entities.temp.FormaPagamento;
import br.com.douglas.entity.model.RelacaoPagamentosDTO;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.repository.repositories.formapagamento.FormaPagamentoRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FormaPagamentoService extends BaseService<FormaPagamento> {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        super(formaPagamentoRepository);
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public FormaPagamento save(FormaPagamento formaPagamento) throws DomainException {
        validate(formaPagamento);
        if(Objects.isNull(formaPagamento.getAtivo()))
            formaPagamento.setAtivo(true);
        return formaPagamentoRepository.save(formaPagamento);
    }

    public FormaPagamento inativarFormaPagamento(FormaPagamento formaPagamento){
        formaPagamento.setAtivo(false);
        return formaPagamentoRepository.save(formaPagamento);
    }

    public Page<FormaPagamento> findAll(Pageable pageable, String nome, String ativo){
        if(Objects.isNull(nome) || nome.equals("null") || nome.equals("") || nome.equals("undefined"))
            return formaPagamentoRepository.findAllByAtivo(pageable, Boolean.valueOf(ativo));
        else
            return formaPagamentoRepository.findAllWithParams(pageable, nome, Boolean.valueOf(ativo));
    }

    @Override
    public void validate(FormaPagamento formaPagamento) throws DomainException{
        if(formaPagamento.getNome().isEmpty() || formaPagamento.getNome().length() < 2){
            throw new DomainException("Nome inválido");
        }
    }

    public FormaPagamento convert(FormaPagamento entity){
        return FormaPagamento.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .ativo(entity.getAtivo())
                .build();
     }

    public void alterarStatus(String id) {
        Optional<FormaPagamento> optFormaPagamento = formaPagamentoRepository.findById(id);
        if(optFormaPagamento.isPresent()) {
            optFormaPagamento.get().setAtivo(!optFormaPagamento.get().getAtivo());
            formaPagamentoRepository.save(optFormaPagamento.get());
        }
    }

    public List<RelacaoPagamentosDTO> getRelacaoPagamentos() {
        return formaPagamentoRepository.agruparPagamentosPorFormaDePagamento();
    }

    public List<RelacaoPagamentosDTO> getRelacaoPagamentosTransportadora() {
        List<RelacaoPagamentosDTO> relacaoPagamentosDTOS = formaPagamentoRepository.agruparPagamentosPorTransportadora2();
        if(relacaoPagamentosDTOS.size() > 4){
            return relacaoPagamentosDTOS.subList(0, 4);
        }
        return relacaoPagamentosDTOS;
    }
}
