package com.epam.cwlhub.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    T insert(T entity);
    void deleteById(long id);
    Optional<T> findById(long id);
    void update(T entity);
    List<T> findAll();
}
