package com.epam.cwlhub.services;

import com.epam.cwlhub.entities.snippet.Snippet;
import java.util.List;

public interface SnippetService extends BaseService<Snippet> {
    List<Snippet> findByGroupId(Long id);

    Snippet findByFileName(String fileName);

    Snippet findByFileNameInGroup(String fileName, Long groupId);
}
