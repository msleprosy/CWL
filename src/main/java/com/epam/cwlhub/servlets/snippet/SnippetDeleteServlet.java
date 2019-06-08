package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.cwlhub.constants.Endpoints.DELETE_SNIPPET_URL;

@WebServlet(name = "DeleteSnippet", urlPatterns = DELETE_SNIPPET_URL)
public class SnippetDeleteServlet extends HttpServlet {
    private final SnippetService snippetService = SnippetServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameterMap().containsKey("id")) {
            long id = Long.parseLong(req.getParameter("id"));
            snippetService.deleteById(id);

            resp.sendRedirect(req.getHeader("referer"));
        }
    }
}