package com.epam.cwlhub.services;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.services.BaseService;

import java.util.List;
import java.util.Optional;

public interface SnippetService extends BaseService<Snippet> {
    List<Snippet> findByGroupId(Long id);
    Optional<Snippet> findByFileName(String fileName);
}
