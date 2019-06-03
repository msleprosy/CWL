package com.epam.cwlhub.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    T insert(T entity);
    void deleteById(Long id);
    Optional<T> findById(Long id);
    void update(T entity);
    List<T> findAll();
}
