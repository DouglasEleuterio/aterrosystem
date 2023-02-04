package br.com.douglas.service.impls;

import br.com.douglas.entity.base.BaseEntity;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.repository.BaseRepository;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;

public abstract class BaseFindService<T extends BaseEntity> implements IBaseService<T> {
    private static final String ERROR_PROPERTY = "error.notfound";
    private final BaseRepository<T> repository;

    protected BaseFindService(final BaseRepository<T> repository) {
        this.repository = repository;
    }

    public Page<T> findAll(@Nullable Specification<T> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    public List<T> findAll(@Nullable Specification<T> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public T findById(String id) throws DomainException {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(Message.toLocale(ERROR_PROPERTY, id)));
    }

    public BaseRepository<T> getRepository() {
        return this.repository;
    }
}
