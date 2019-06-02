package com.epam.cwlhub.servlets;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static com.epam.cwlhub.constants.Endpoints.SNIPPET_VIEW;

@WebServlet(name = "UpdateSnippet", urlPatterns = "/update")
public class SnippetUpdateServlet extends HttpServlet {
    private static final String SNIPPET_NAME = "name";
    private static final String CONTENT = "content";
    private static final String TAG = "tag";
    private static final LocalDate MODIFICATION_DATE = LocalDate.now();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SnippetService snippetService = SnippetServiceImpl.getInstance();

        if (req.getParameterMap().containsKey("id")) {
            Long id = Long.parseLong(req.getParameter("id"));
            Optional<Snippet> receivedSnippet = snippetService.findById(id);

            if (receivedSnippet.isPresent()) {
                Snippet snippet = receivedSnippet.get();

                String newName = req.getParameter(SNIPPET_NAME).trim();
                String newTag = req.getParameter(TAG).trim();
                String newContent = req.getParameter(CONTENT).trim();

                snippet.setName(newName);
                snippet.setTag(newTag);
                snippet.setContent(newContent);
                snippet.setModificationDate(MODIFICATION_DATE);

                snippetService.update(snippet);
                resp.sendRedirect(req.getHeader("referer"));
            }
        }
    }
}
