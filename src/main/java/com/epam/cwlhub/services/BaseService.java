package com.epam.cwlhub.services;

import java.util.List;

public interface BaseService<T> {
    T insert(T entity);
    void deleteById(Long id);
    T findById(Long id);
    void update(T entity);
    List<T> findAll();
}
