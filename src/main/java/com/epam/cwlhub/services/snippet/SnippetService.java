package com.epam.cwlhub.services.snippet;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.services.BaseService;

import java.util.List;

public interface SnippetService extends BaseService<Snippet> {
    List<Snippet> findByGroupId(Long id);
}
