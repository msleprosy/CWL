package com.epam.cwlhub.servlets;

import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.cwlhub.constants.Endpoints.DELETE_SNIPPET_URL;

@WebServlet(name = "DeleteSnippet", urlPatterns = DELETE_SNIPPET_URL)
public class SnippetDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SnippetService snippetService = SnippetServiceImpl.getInstance();

        if (req.getParameterMap().containsKey("id")) {
            Long id = Long.parseLong(req.getParameter("id"));
            snippetService.deleteById(id);

            resp.sendRedirect(req.getHeader("referer"));
        }
    }
}

