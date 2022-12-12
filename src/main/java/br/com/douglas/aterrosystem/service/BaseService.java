package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.BaseRepository;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public abstract class BaseService <T> {

    private BaseRepository repository;

    public BaseService(final BaseRepository repository) {
        this.repository = repository;
    }

    public List<T> findAll(Sort sort){
        return repository.findAll(sort);
    }

    public T save(T t) throws DomainException {
        validate(t);
        return (T) repository.save(t);
    }

    public T findById(Long id) throws DomainException {
        Optional byId = repository.findById(id);
        if(!byId.isPresent()){
            throw new DomainException("Registro não encontrado!");
        }
        return (T) byId.get();
    }

    public <T extends BaseRepository> T getRepository(){
        return (T) repository;
    }

    abstract void validate(T t) throws DomainException;
}
