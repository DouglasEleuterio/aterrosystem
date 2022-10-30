package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Transportador;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.TransportadorRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransportadorService {

    private final TransportadorRepository repository;
    private final EnderecoService enderecoService;

    public TransportadorService(TransportadorRepository repository, EnderecoService enderecoService) {
        this.repository = repository;
        this.enderecoService = enderecoService;
    }

    public Transportador save(Transportador transportador) throws DomainException {
        validate(transportador);
        return repository.save(transportador);
    }

    private void validate(Transportador transportador) throws DomainException{
        if(Objects.isNull(transportador.getEndereco())){
            throw new DomainException("Endereço inválido");
        }
        if(Objects.isNull(transportador.getNome()) || transportador.getNome().length() < 3){
            throw new DomainException("Nome inválido");
        }
        if(Objects.isNull(transportador.getRazaoSocial()) || transportador.getRazaoSocial().length() < 3){
            throw new DomainException("Razão Social Inválida");
        }
        if(Objects.isNull(transportador.getCnpj()) || transportador.getCnpj().length() < 14){
            throw new DomainException("CNPJ Inválido");
        }
        enderecoService.validate(transportador.getEndereco());
    }
}
