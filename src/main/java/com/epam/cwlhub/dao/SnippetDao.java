package com.epam.cwlhub.dao;

import com.epam.cwlhub.entities.snippet.Snippet;

import java.util.List;

public interface SnippetDao extends BaseDao<Snippet> {
    List<Snippet> findByGroupId(long id);
    Snippet findByFileName(String fileName);
}
