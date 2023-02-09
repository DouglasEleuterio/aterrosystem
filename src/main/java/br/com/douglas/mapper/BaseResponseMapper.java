package br.com.douglas.mapper;

import br.com.douglas.entity.base.BaseEntity;

import java.util.List;

public interface BaseResponseMapper <T extends BaseEntity, P> {
    P toResponse(T entity);
    List<P> toResponseList(List<T> entityList);
}
