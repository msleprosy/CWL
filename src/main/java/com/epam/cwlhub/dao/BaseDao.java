package com.epam.cwlhub.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<ENTITY_TYPE> {
    ENTITY_TYPE insert(ENTITY_TYPE entity);
    void deleteById(long id);
    Optional<ENTITY_TYPE> findById(long id);
    void update(ENTITY_TYPE entity);
    List<ENTITY_TYPE> findAll();
}
