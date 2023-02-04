package br.com.douglas.service.interfaces;

import br.com.douglas.entity.base.BaseEntity;
import br.com.douglas.exception.exceptions.DomainException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IbaseFindService <T extends BaseEntity> {
    T findById(String id) throws DomainException;

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    List<T> findAll(@Nullable Specification<T> specification, Sort sort);
    List<T> findAll(Sort sort);
    Page<T> findAll(@Nullable Specification<T> specification, Pageable pageable);
}
