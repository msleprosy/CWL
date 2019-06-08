package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.exceptions.unchecked.SnippetException;
import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.cwlhub.constants.Endpoints.*;

@WebServlet(name = "AdminSnippetViewServlet", urlPatterns = ADMIN_SNIPPET_VIEW_URL)
public class AdminSnippetViewServlet extends HttpServlet {
    private final SnippetService snippetService = SnippetServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            long id = Long.parseLong(req.getParameter("id"));
            try {
                Snippet snippetView = snippetService.findById(id);
                req.setAttribute("snippet", snippetView);
                req.getRequestDispatcher(ADMIN_SNIPPET_VIEW).forward(req, resp);
            } catch (SnippetException e) {
                req.getRequestDispatcher(PAGE_NOT_FOUND).forward(req, resp);
            }
        }
    }
}
