package com.epam.cwlhub.dao;

import com.epam.cwlhub.dao.BaseDao;
import com.epam.cwlhub.entities.snippet.Snippet;

import java.util.List;
import java.util.Optional;

public interface SnippetDao extends BaseDao<Snippet> {
    List<Snippet> findByGroupId(long id);
    Optional<Snippet> findByFileName(String fileName);
}
