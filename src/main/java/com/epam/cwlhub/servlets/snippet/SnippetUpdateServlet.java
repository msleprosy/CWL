package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.epam.cwlhub.constants.Endpoints.UPDATE_SNIPPET_URL;

@WebServlet(name = "UpdateSnippet", urlPatterns = UPDATE_SNIPPET_URL)
public class SnippetUpdateServlet extends HttpServlet {
    private final SnippetService snippetService = SnippetServiceImpl.getInstance();
    private static final String SNIPPET_NAME = "name";
    private static final String CONTENT = "content";
    private static final String TAG = "tag";
    private static final LocalDate MODIFICATION_DATE = LocalDate.now();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameterMap().containsKey("id")) {
            long id = Long.parseLong(req.getParameter("id"));

            Snippet snippet = snippetService.findById(id);
            mapUpdatedParametersToSnippet(req, snippet);
            snippetService.update(snippet);

            resp.sendRedirect(req.getHeader("referer"));
        }
    }

    private void mapUpdatedParametersToSnippet(HttpServletRequest req, Snippet snippet) {
        snippet.setName(req.getParameter(SNIPPET_NAME).trim());
        snippet.setTag(req.getParameter(TAG).trim());
        snippet.setContent(req.getParameter(CONTENT).trim());
        snippet.setModificationDate(MODIFICATION_DATE);
    }
}
