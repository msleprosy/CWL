package com.epam.cwlhub.servlets.group;

import com.epam.cwlhub.dao.group.GroupException;
import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.services.group.GroupService;
import com.epam.cwlhub.services.group.GroupServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ViewAllGroups", urlPatterns = "/groups/all")
public class ViewAllGroupsServlet {

    private GroupService groupService = GroupServiceImpl.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Group> groups = groupService.findAll();

            if (!groups.isEmpty()) {
                req.setAttribute("groups", groups);
            }
                forwardToPage(req, resp, "allGroups.jsp");

            } catch (Exception e) {
                throw new GroupException("Can't display all groups", e);

            }
        }
    private void forwardToPage(HttpServletRequest req, HttpServletResponse resp, String dest) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        Map<String, Long> userSessionData = (Map<String, Long>) context.getAttribute("UserSessionData");
        RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/jsp/" + dest);
        dispatcher.forward(req, resp);
    }
}

