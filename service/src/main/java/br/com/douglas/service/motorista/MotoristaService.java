package br.com.douglas.service.motorista;

import br.com.douglas.entity.entities.temp.Motorista;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.repository.repositories.motorista.MotoristaRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MotoristaService extends BaseService<Motorista> {
    private final MotoristaRepository motoristaRepository;

    public MotoristaService(MotoristaRepository motoristaRepository) {
        super(motoristaRepository);
        this.motoristaRepository = motoristaRepository;
    }


    public Motorista save(Motorista entity) throws DomainException {
        validate(entity);
        return motoristaRepository.save(entity);
    }

    public void validate (Motorista entity) throws DomainException {
        if(Objects.isNull(entity.getNome()) || entity.getNome().isEmpty())
            throw new DomainException("Nome obrigatório");
        if(Objects.isNull(entity.getTelefone()) || entity.getTelefone().isEmpty())
            throw new DomainException("Telefone obrigatório");
    }
}
