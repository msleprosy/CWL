package com.epam.cwlhub.services.impl;

import com.epam.cwlhub.dao.SnippetDao;
import com.epam.cwlhub.dao.impl.SnippetDaoImpl;
import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.services.SnippetService;

import java.util.*;

public class SnippetServiceImpl implements SnippetService {
    private final SnippetDao snippetDao = SnippetDaoImpl.getInstance();

    private static volatile SnippetServiceImpl INSTANCE;

    public static SnippetServiceImpl getInstance() {
        SnippetServiceImpl localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (SnippetServiceImpl.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new SnippetServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Snippet insert(Snippet snippet) {
        if (snippet != null) {
            snippetDao.insert(snippet);
        }
        return snippet;
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            snippetDao.deleteById(id);
        }
    }

    @Override
    public Optional<Snippet> findById(Long id) {
        if (id != null) {
            return snippetDao.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public List<Snippet> findByGroupId(Long id) {
        List<Snippet> result = new ArrayList<>();
        if (id != null) {
            result = snippetDao.findByGroupId(id);
            result.sort(Comparator.comparingLong(Snippet::getId));
        }
        return result;
    }

    @Override
    public void update(Snippet snippet) {
        if (snippet != null) {
            snippetDao.update(snippet);
        }
    }

    @Override
    public List<Snippet> findAll() {
        return snippetDao.findAll();
    }
}
