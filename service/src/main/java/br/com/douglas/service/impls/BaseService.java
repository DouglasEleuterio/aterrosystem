package br.com.douglas.service.impls;

import br.com.douglas.entity.base.BaseEntity;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.repository.BaseRepository;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

public abstract class BaseService<T extends BaseEntity> implements IBaseService<T> {

    private static final String ERROR_PROPERTY = "error.notfound";
    private final BaseRepository<T> repository;

    protected BaseService(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T create(T entity) throws DomainException {
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T update(String id, T entity) throws DomainException {
        final T t = repository.findById(id)
                .orElseThrow(() ->
                        new DomainException(Message.toLocale(ERROR_PROPERTY, id)));
        validate(entity);
        bind(t, entity);
        return repository.save(t);
    }

    @Override
    public void delete(String id) throws DomainException {
        final T t = repository.findById(id)
                .orElseThrow(() -> new DomainException(Message.toLocale(ERROR_PROPERTY, id)));
        repository.delete(t);
    }

    @Override
    public void bind(T entity, T update) {
        BeanUtils.copyProperties(update, entity, "id");
    }

    @Override
    public void validate(T entity) throws  DomainException {
        if(Objects.isNull(entity))
            throw new DomainException(Message.toLocale("error.entity-null"));
    }

    @Override
    public T findById(String id) throws DomainException {
        return repository.findById(id).orElseThrow(() -> new DomainException(Message.toLocale(ERROR_PROPERTY, id)));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<T> findAll(Specification<T> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }
}
