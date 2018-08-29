package com.windcoder.qycms.service;

import com.windcoder.qycms.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

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

    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable){

        return repository.findAll(Example.of(example.getProbe()), pageable);
    }

    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }
}
