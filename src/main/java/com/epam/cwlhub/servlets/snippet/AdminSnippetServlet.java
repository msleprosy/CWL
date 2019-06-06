package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.epam.cwlhub.constants.Endpoints.ADMIN_SNIPPETS;
import static com.epam.cwlhub.constants.Endpoints.ADMIN_SNIPPETS_URL;

@WebServlet(name = "AdminSnippetServlet", urlPatterns = ADMIN_SNIPPETS_URL)
public class AdminSnippetServlet extends HttpServlet {
    private final SnippetService snippetService = SnippetServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Snippet> commonSnippets = snippetService.findAll();
        req.setAttribute("snippets", commonSnippets);

        req.getRequestDispatcher(ADMIN_SNIPPETS).forward(req, resp);
    }
}
