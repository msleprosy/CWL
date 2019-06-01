package com.epam.cwlhub.servlets.group;

import com.epam.cwlhub.dao.group.GroupException;
import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.services.group.GroupService;
import com.epam.cwlhub.services.group.GroupServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "ViewUsersGroups", urlPatterns = "/usersgroups")
public class ViewUsersGroupsServlet {

    private GroupService groupService = GroupServiceImpl.getInstance();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Group> groups = groupService.findUsersGroups(getUserId(req).orElse(0L));

            if (!groups.isEmpty()) {
                req.setAttribute("groups", groups);
            }
            forwardToPage(req, resp, "usersGroups.jsp");

        } catch (Exception e) {
            throw new GroupException("Can't display user's groups", e);

        }
    }

    private void forwardToPage(HttpServletRequest req, HttpServletResponse resp, String dest) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/" + dest);
        dispatcher.forward(req, resp);
    }

    private Optional<Long> getUserId(HttpServletRequest request) {
        try {
            return java.util.Optional.of(java.lang.Long.parseLong(request.getParameter("user_id")));
        } catch (Exception e) {
            return java.util.Optional.empty();
        }
    }

    private long parseUserId(Optional<Long> longOptional) {
        try {
            if (longOptional.isPresent()) {
                return longOptional.get();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new GroupException("There is no user_id", e);
        }

    }
}
