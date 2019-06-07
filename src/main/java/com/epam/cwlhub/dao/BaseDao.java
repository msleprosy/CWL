package com.epam.cwlhub.dao;

import java.util.List;

public interface BaseDao<T> {
    T insert(T entity);
    void deleteById(long id);
    T findById(long id);
    void update(T entity);
    List<T> findAll();
}

