package com.epam.cwlhub.servlets.group;

import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.exceptions.unchecked.GroupException;
import com.epam.cwlhub.services.GroupService;
import com.epam.cwlhub.services.impl.GroupServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewUsersGroups", urlPatterns = "/usersgroups")
public class ViewUsersGroupsServlet extends HttpServlet {

    private String TARGET_PAGE_ROOT = "/WEB-INF/jsp/";
    private GroupService groupService = GroupServiceImpl.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Group> groups = groupService.findUsersGroups(getUserId(req));
            if (!groups.isEmpty()) {
                req.setAttribute("groups", groups);
            }
            forwardToPage(req, resp, "usersGroups.jsp");
        } catch (Exception e) {
            throw new GroupException("Can't display user's groups", e);
        }
    }

    private void forwardToPage(HttpServletRequest req, HttpServletResponse resp, String dest)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(TARGET_PAGE_ROOT + dest);
        dispatcher.forward(req, resp);
    }

    private Long getUserId(HttpServletRequest request) {
        try {
            return Long.parseLong(request.getParameter("user_id"));
        } catch (Exception e) {
            throw new GroupException("Can't parse user_id parameter from request");
        }
    }

}
