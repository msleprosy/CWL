package com.epam.cwlhub.servlets.group;

import com.epam.cwlhub.services.GroupService;
import com.epam.cwlhub.services.impl.GroupServiceImpl;
import com.epam.cwlhub.entities.group.Group;

import javax.servlet.RequestDispatcher;
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

@WebServlet(name = "CreateNewGroupServlet", urlPatterns = CREATE_GROUP_URL)
public class CreateNewGroupServlet extends HttpServlet {
    private final GroupService groupService = GroupServiceImpl.getInstance();
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter(NAME).trim();
        String description = req.getParameter(DESCRIPTION).trim();

        List<Group> groups = groupService.findAll();
        if (!groups.isEmpty()) {
            req.setAttribute("groups", groups);
        }

        if (name.equals("") || description.equals("")) {
            req.setAttribute("errorMessage", "Empty fields are not allowed!");
            RequestDispatcher dispatcher = req.getRequestDispatcher(ALL_GROUPS);
            dispatcher.forward(req, resp);
        } else if ((!name.matches("^[a-zA-Z0-9_-]+( [a-zA-Z0-9_-]+)*$") || name.length() > 30)) {
            req.setAttribute("errorMessage", "Invalid group name! It may only contain letters, numbers, \"_\" and  \"-\" and must be less than 30 symbols.");
            RequestDispatcher dispatcher = req.getRequestDispatcher(ALL_GROUPS);
            dispatcher.forward(req, resp);
        } else if ((!description.matches("^[a-zA-Z0-9_-]+( [a-zA-Z0-9_-]+)*$") || description.length() > 150)) {
            req.setAttribute("errorMessage", "Invalid description! It may only contain letters, numbers, \"_\" and  \"-\" and must be less than 30 symbols.");
            RequestDispatcher dispatcher = req.getRequestDispatcher(ALL_GROUPS);
            dispatcher.forward(req, resp);
        } else {
            Group group = groupService.findGroupByName(name);
            if (group == null) {
                Group newGroup = new Group();
                setGroupParameters(req, newGroup);
                groupService.insert(newGroup);
                resp.sendRedirect(ALL_GROUPS_URL);
            } else {
                req.setAttribute("errorMessage", "This group already exists in the database! Please choose another name.");
                RequestDispatcher dispatcher = req.getRequestDispatcher(ALL_GROUPS);
                dispatcher.forward(req, resp);
            }
        }
    }

    private void setGroupParameters(HttpServletRequest req, Group newGroup) {
        Long userId = ((Map<String, Long>) req.getServletContext().getAttribute(USER_SESSION_DATA))
                .get(req.getSession().getId());
        newGroup.setName(req.getParameter(NAME).trim());
        newGroup.setDescription(req.getParameter(DESCRIPTION).trim());
        newGroup.setCreatorId(userId);
    }
}

