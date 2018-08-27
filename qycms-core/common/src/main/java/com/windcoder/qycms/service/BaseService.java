package com.windcoder.qycms.service;

import com.windcoder.qycms.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;


public class BaseService <T, ID extends Serializable, R extends SupportRepository<T, ID>> {
    private Class<T> clazz;
    protected R repository;

    public BaseService() {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.clazz = clazz;
    }

    @Autowired
    public <S extends R> void setRepository(S repository) {
        this.repository = repository;
    }


    public <S extends T> S save(S entity) {
        return repository.saveAndFlush(entity);
    }

    public T findOne(ID id) {
        return repository.findOne(id);
    }
}
