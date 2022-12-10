package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Motorista;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.MotoristaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoristaService {
    private final MotoristaRepository motoristaRepository;

    public MotoristaService(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    public void delete(Long id) {
        motoristaRepository.deleteById(id);
    }

    public List<Motorista> findAll(Sort sort){
        return motoristaRepository.findAll(sort);
    }

    public Motorista save(Motorista entity) throws DomainException {
        validate(entity);
        return motoristaRepository.save(entity);
    }

    private void validate (Motorista entity) throws DomainException {
        if(entity.getNome().isEmpty())
            throw new DomainException("Nome obrigatório");
        if(entity.getTelefone().isEmpty())
            throw new DomainException("Telefone obrigatório");
    }
}
