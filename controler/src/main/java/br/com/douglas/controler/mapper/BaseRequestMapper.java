package br.com.douglas.controler.mapper;

import br.com.douglas.entity.base.BaseEntity;

public interface BaseRequestMapper <T extends BaseEntity, P> {
    T fromRequest(P request);

    P toRequest(T entity);
}
