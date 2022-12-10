package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Gerador;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.GeradorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GeradorService {

    private final GeradorRepository repository;

    public GeradorService(GeradorRepository repository) {
        this.repository = repository;
    }

    public Gerador save(Gerador gerador) throws DomainException {
        validate(gerador);
        gerador.setAtivo(true);
        return repository.save(gerador);
    }

    private void validate(Gerador gerador) throws DomainException {
        if(Objects.isNull(gerador.getRetirada())){
            throw new DomainException("Endereço de Retirada inválida");
        }
        if((Objects.isNull(gerador.getNome()) || gerador.getNome().length() < 2) && (Objects.isNull(gerador.getRazaoSocial()) || gerador.getRazaoSocial().length() < 3)){
            throw new DomainException("Nome/Razão Social inválido");
        }
        if(Objects.isNull(gerador.getCpf()) && Objects.isNull(gerador.getCnpj())){
            throw new DomainException("Necessário informar documento CPF ou CNPJ");
        }
        if(Objects.isNull(gerador.getCpf()) && (Objects.nonNull(gerador.getCpf()) && gerador.getCpf().length() < 11)){
            throw new DomainException("CPF inválido");
        }
        if(Objects.isNull(gerador.getCnpj()) && (Objects.nonNull(gerador.getCnpj()) && gerador.getCnpj().length() < 14)){
            throw new DomainException("CNPJ inválido");
        }
    }

    public List<Gerador> findAll(Sort sort) {
        return repository.findAll();
    }
}
