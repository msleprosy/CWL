package com.epam.cwlhub.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<ENTITY_TYPE> {
    ENTITY_TYPE insert(ENTITY_TYPE entity);
    void deleteById(Long id);
    Optional<ENTITY_TYPE> findById(Long id);
    void update(ENTITY_TYPE entity);
    List<ENTITY_TYPE> findAll();
}
