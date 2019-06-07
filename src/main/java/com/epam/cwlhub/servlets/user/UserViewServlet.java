package com.epam.cwlhub.servlets.user;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.exceptions.unchecked.UserException;
import com.epam.cwlhub.services.UserService;
import com.epam.cwlhub.services.impl.UserServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.cwlhub.constants.Endpoints.USER_VIEW;
import static com.epam.cwlhub.constants.Endpoints.USER_VIEW_URL;

@WebServlet(name = "UserViewServlet", urlPatterns = USER_VIEW_URL)

public class UserViewServlet extends HttpServlet {

    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<UserEntity> users = userService.findAll();
            if (!users.isEmpty()) {
                List<UserEntity> usersWithoutAdmin = users.stream().filter(user -> user.getId() != 1).collect(Collectors.toList());
                req.setAttribute("user", usersWithoutAdmin);
            }
        req.getRequestDispatcher(USER_VIEW).forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new UserException("Can't display all users", e);
        }
    }
}
