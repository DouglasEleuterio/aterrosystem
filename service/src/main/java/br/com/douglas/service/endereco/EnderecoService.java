package br.com.douglas.service.endereco;

import br.com.douglas.entity.entities.temp.Endereco;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.repository.repositories.endereco.EnderecoRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EnderecoService extends BaseService<Endereco> {

    private final EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Endereco save(Endereco formaPagamento) throws DomainException {
        validate(formaPagamento);
        return repository.save(formaPagamento);
    }

    @Override
    public void validate(Endereco endereco) throws DomainException{
        if(Objects.isNull(endereco.getLogradouro()) || endereco.getLogradouro().isEmpty() || endereco.getLogradouro().length() < 3){
            throw new DomainException("Logradouro Inválido");
        }
        if(Objects.isNull(endereco.getNumero()) && Objects.isNull(endereco.getComplemento())){
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
