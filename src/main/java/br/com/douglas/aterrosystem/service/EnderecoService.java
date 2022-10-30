package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Endereco;
import br.com.douglas.aterrosystem.entity.FormaPagamento;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EnderecoService {

    private final EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public Endereco save(Endereco formaPagamento) throws DomainException {
        validate(formaPagamento);
        return repository.save(formaPagamento);
    }

    public void validate(Endereco endereco) throws DomainException{
        if(Objects.isNull(endereco.getLogradouro()) || endereco.getLogradouro().isEmpty() || endereco.getLogradouro().length() < 3){
            throw new DomainException("Logradouro Inválido");
        }
        if(Objects.isNull(endereco.getNumero()) || endereco.getNumero().isEmpty() && endereco.getComplemento().isEmpty()){
            throw new DomainException("Por favor, informar número ou complemento!");
        }
        if(Objects.isNull(endereco.getCidade()) || endereco.getCidade().isEmpty() || endereco.getCidade().length() < 2){
            throw new DomainException("Cidade Inválida");
        }
        if(Objects.isNull(endereco.getEstado()) || endereco.getEstado().isEmpty() || endereco.getEstado().length() < 2){
            throw new DomainException("Estado inválido");
        }
    }
}
