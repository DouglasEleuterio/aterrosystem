package br.com.douglas.controler.mapper;

import br.com.douglas.entity.base.BaseEntity;

import java.util.List;

/**
 *
 * @param <T> - Entidade;
 * @param <R> - Request
 * @param <P> - ResPosta
 */
public interface BaseMapper <T extends BaseEntity, R, P> {

    T fromRequest(R request);
    P toResponse(T entity);

    List<P> toResponseList(List<T> list);

}
