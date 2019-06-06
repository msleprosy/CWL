package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.cwlhub.constants.Endpoints.ADMIN_DELETE_SNIPPET_URL;

@WebServlet(name = "AdminSnippetDeleteServlet", urlPatterns = ADMIN_DELETE_SNIPPET_URL)
public class AdminSnippetDeleteServlet extends HttpServlet {
    private final SnippetService snippetService = SnippetServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            Long id = Long.parseLong(req.getParameter("id"));
            snippetService.deleteById(id);

            resp.sendRedirect(req.getHeader("referer"));
        }
    }
}
