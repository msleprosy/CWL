package com.epam.cwlhub.servlets.group;

import com.epam.cwlhub.exceptions.unchecked.GroupException;
import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.services.GroupService;
import com.epam.cwlhub.services.impl.GroupServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.cwlhub.constants.Endpoints.*;

@WebServlet(name = "ViewAllAdminGroups", urlPatterns = ALL_ADMIN_GROUPS_URL)
public class ViewAllGroupsServlet extends HttpServlet {
    private GroupService groupService = GroupServiceImpl.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Group> groups = groupService.findAll();
            if (!groups.isEmpty()) {
                req.setAttribute("groups", groups);
            }
            forwardToPage(req, resp, ALL_ADMIN_GROUPS);
        } catch (ServletException | IOException e) {
            throw new GroupException("Can't display all groups", e);
        }
    }

    private void forwardToPage(HttpServletRequest req, HttpServletResponse resp, String dest)
            throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(ALL_ADMIN_GROUPS);
        dispatcher.forward(req, resp);

    }
}