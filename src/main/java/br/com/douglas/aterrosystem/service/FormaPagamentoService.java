package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.RelacaoPagamentosDTO;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.entity.FormaPagamento;
import br.com.douglas.aterrosystem.repository.FormaPagamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public FormaPagamento save(FormaPagamento formaPagamento) throws DomainException{
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

    private void validate(FormaPagamento formaPagamento) throws DomainException{
        if(formaPagamento.getNome().isEmpty() || formaPagamento.getNome().length() < 2){
            throw new DomainException("Nome inválido");
        }
    }

    public FormaPagamento convert(FormaPagamento entity){
        FormaPagamento response = FormaPagamento.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .ativo(entity.getAtivo())
                .build();
        return response;
    }

    public void alterarStatus(Long id) {
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
