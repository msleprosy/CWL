package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.exceptions.unchecked.SnippetException;
import com.epam.cwlhub.services.GroupService;
import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.GroupServiceImpl;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.epam.cwlhub.constants.Endpoints.*;
import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

@WebServlet(name = "SnippetView", urlPatterns = SNIPPET_VIEW_URL)
public class SnippetViewServlet extends HttpServlet {
    private final SnippetService snippetService = SnippetServiceImpl.getInstance();
    private final GroupService groupService = GroupServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = ((Map<String, Long>) req.getServletContext().getAttribute(USER_SESSION_DATA))
                .get(req.getSession().getId());
        UserEntity user = UserServiceImpl.getInstance().findById(userId);

        List<Group> userGroups = groupService.findUsersGroups(user.getId());
        req.setAttribute("userGroups", userGroups);

        if (req.getParameterMap().containsKey("id")) {
            long id = Long.parseLong(req.getParameter("id"));
            try {
                Snippet snippetView = snippetService.findById(id);
                req.setAttribute("snippet", snippetView);
                req.getRequestDispatcher(SNIPPET_VIEW).forward(req, resp);
            } catch (SnippetException e) {
                req.getRequestDispatcher(PAGE_NOT_FOUND).forward(req, resp);
            }
        }
    }
}