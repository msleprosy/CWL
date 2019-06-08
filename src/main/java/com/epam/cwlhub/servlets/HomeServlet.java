package com.epam.cwlhub.servlets;

import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.GroupService;
import com.epam.cwlhub.services.SnippetService;
import com.epam.cwlhub.services.impl.GroupServiceImpl;
import com.epam.cwlhub.services.impl.SnippetServiceImpl;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.epam.cwlhub.constants.Endpoints.HOME;
import static com.epam.cwlhub.constants.Endpoints.HOME_URL;
import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

@WebServlet(name = "HomeServlet", urlPatterns = HOME_URL)
public class HomeServlet extends HttpServlet {
    private final SnippetService snippetService = SnippetServiceImpl.getInstance();
    private final GroupService groupService = GroupServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = ((Map<String, Long>) req.getServletContext().getAttribute(USER_SESSION_DATA))
                .get(req.getSession().getId());
        UserEntity receivedUser = UserServiceImpl.getInstance().findById(id);
        List<Snippet> commonSnippets;
        if (req.getParameterMap().containsKey("page")){
            Integer page = Integer.parseInt(req.getParameter("page"));
            commonSnippets = snippetService.getRecords(page,1L);
        } else {
            Integer page = 1;
            commonSnippets = snippetService.getRecords(page,1L);
        }
        req.setAttribute("snippets", commonSnippets);

        List<Group> userGroups = groupService.findUsersGroups(receivedUser.getId());
        req.setAttribute("userGroups", userGroups);

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(HOME);
        dispatcher.forward(req, resp);
    }
}
