package br.com.douglas.service.interfaces;

import br.com.douglas.entity.base.BaseEntity;
import br.com.douglas.exception.exceptions.DomainException;

public interface IBaseService<T extends BaseEntity> extends IbaseFindService<T> {
    T create(T entity) throws DomainException;
    T update(String id, T entity) throws DomainException;
    void delete(String id) throws DomainException;
    void bind(T entity, T update);
    void validate(T entity) throws DomainException;
}