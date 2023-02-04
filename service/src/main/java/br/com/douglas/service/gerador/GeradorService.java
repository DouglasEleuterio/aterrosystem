package br.com.douglas.service.gerador;


import br.com.douglas.entity.entities.temp.Gerador;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.repository.repositories.gerador.GeradorRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GeradorService extends BaseService<Gerador> {

    private final GeradorRepository repository;

    public GeradorService(GeradorRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Gerador save(Gerador gerador) throws DomainException {
        validate(gerador);
        gerador.setAtivo(true);
        return repository.save(gerador);
    }

    @Override
    public void validate(Gerador gerador) throws DomainException {
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

    public Page<Gerador> findAll(Pageable paging, String nome, String cpf, String cnpj, String email, String ativoP) {
        Boolean ativo = Boolean.valueOf(ativoP);
        return repository.findAll(paging);
    }
}
