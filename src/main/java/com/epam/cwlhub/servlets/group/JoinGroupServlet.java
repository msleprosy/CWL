package com.epam.cwlhub.servlets.group;

import com.epam.cwlhub.services.GroupService;
import com.epam.cwlhub.services.impl.GroupServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.epam.cwlhub.constants.Endpoints.*;
import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

@WebServlet(name="JoinGroupServlet", urlPatterns = JOIN_GROUP_URL)
public class JoinGroupServlet extends HttpServlet {

    private final GroupService groupService = GroupServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                .get(request.getSession().getId());
        groupService.joinGroup(userId, Long.parseLong(request.getParameter("id")));
        response.sendRedirect(ALL_GROUPS_URL);
    }
}



