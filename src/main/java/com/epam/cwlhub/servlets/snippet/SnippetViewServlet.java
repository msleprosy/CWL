package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.epam.cwlhub.constants.Endpoints.SNIPPET_VIEW;
import static com.epam.cwlhub.constants.Endpoints.SNIPPET_VIEW_URL;

@WebServlet(name = "SnippetView", urlPatterns = SNIPPET_VIEW_URL)
public class SnippetViewServlet extends HttpServlet {
    private final SnippetService snippetService = SnippetServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            Long id = Long.parseLong(req.getParameter("id"));
            Snippet snippetView = snippetService.findById(id);
            req.setAttribute("snippet", snippetView);
            RequestDispatcher dispatcher = req.getRequestDispatcher(SNIPPET_VIEW);
            dispatcher.forward(req, resp);
        }
    }
}
