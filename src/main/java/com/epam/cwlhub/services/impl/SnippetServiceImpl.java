package com.epam.cwlhub.services.impl;

import com.epam.cwlhub.dao.SnippetDao;
import com.epam.cwlhub.dao.impl.SnippetDaoImpl;
import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.services.SnippetService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
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
        if (id != null) {
            return snippetDao.findByGroupId(id);
        }
        return Collections.emptyList();
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

    public Snippet createSnippetObjectFromRequest(HttpServletRequest request)
            throws ServletException, IOException {

        String fileName = request.getParameter("fileName");
        String tags = request.getParameter("tags");

        InputStream inputStream = null;
        Part filePart = request.getPart("cwl");
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }

        java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        String content = s.hasNext() ? s.next() : "";
        Snippet snippet = new Snippet();
        snippet.setName(fileName);
        snippet.setOwnerId(2);
        snippet.setGroupId(1);
        snippet.setContent(content);
        snippet.setCreationDate(LocalDate.now());
        snippet.setModificationDate(LocalDate.now());
        snippet.setTag(tags);
        return snippet;
    }
}
