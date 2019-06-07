package com.epam.cwlhub.servlets.group;

import com.epam.cwlhub.services.GroupService;
import com.epam.cwlhub.services.impl.GroupServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.cwlhub.constants.Endpoints.*;

@WebServlet(name="DeleteUserGroupServlet", urlPatterns = DELETE_GROUP_URL_USER)
public class DeleteUserGroupServlet extends HttpServlet {
    private final GroupService groupService = GroupServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        groupService.deleteById(Long.parseLong(request.getParameter("id")));
        response.sendRedirect(ALL_GROUPS_URL);
    }
}



