package com.epam.cwlhub.services.impl;

import com.epam.cwlhub.dao.SnippetDao;
import com.epam.cwlhub.dao.impl.SnippetDaoImpl;
import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.exceptions.unchecked.SnippetException;
import com.epam.cwlhub.services.SnippetService;
import com.sun.xml.internal.bind.v2.TODO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

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
        if (snippet == null) {
            throw new SnippetException("Snippet entity can't be empty");
        }
        return snippetDao.insert(snippet);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new SnippetException("ID can't be empty");
        }
        snippetDao.deleteById(id);
    }

    @Override
    public Snippet findById(Long id) {
        if (id == null) {
            throw new SnippetException("ID can't be empty");
        }
        return snippetDao.findById(id);
    }

    @Override
    public List<Snippet> findByGroupId(Long id) {
        if (id == null) {
            throw new SnippetException("ID can't be empty");
        }
        List<Snippet> result = snippetDao.findByGroupId(id);
        result.sort(Comparator.comparingLong(Snippet::getId));
        return result;
    }

    @Override
    public void update(Snippet snippet) {
        if (snippet == null) {
            throw new SnippetException("Snippet entity can't be empty");
        }
        snippetDao.update(snippet);
    }

    @Override
    public List<Snippet> findAll() {
        return snippetDao.findAll();
    }

    @Override
    public Snippet findByFileName(String fileName) {
        if (fileName == null) {
            throw new SnippetException("File name can't be empty");
        }
        return snippetDao.findByFileName(fileName);
    }

    @Override
    public boolean createSnippetObjectFromRequest(HttpServletRequest request) throws ServletException, IOException {
        Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                .get(request.getSession().getId());
        UserEntity user = UserServiceImpl.getInstance().findById(id);
        if (user != null && request.getParameterMap().containsKey("group_id")) {
            String fileName = request.getParameter("fileName");
            String tags = request.getParameter("tags");

            InputStream inputStream = null;
            Part filePart = request.getPart("cwl");
            if (filePart != null) {
                inputStream = filePart.getInputStream();
            }
            Snippet file = findByFileName(fileName);
            if (file != null) {
                return false;
            }

            String content = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
            Snippet snippet = new Snippet();
            snippet.setName(fileName);
            snippet.setOwnerId(user.getId());
            snippet.setGroupId(Long.parseLong(request.getParameter("group_id")));
            snippet.setContent(content);
            snippet.setCreationDate(LocalDate.now());
            snippet.setModificationDate(LocalDate.now());
            snippet.setTag(tags);
            snippetDao.insert(snippet);
            return true;
        }
        return false;
    }
}
