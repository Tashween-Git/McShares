package com.mcshares.demo.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T> {

    T create(T t);

    /**
     * hibernate query.list() method is returning empty list NOT a null value
     */
    List<T> findAll();

    T find(Serializable id);

    T update(T t);

    int delete(Serializable id);

}
