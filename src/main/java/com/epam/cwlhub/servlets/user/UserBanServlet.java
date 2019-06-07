package com.epam.cwlhub.servlets.user;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.UserService;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "UserBanServlet", urlPatterns = "/banUsers")

public class UserBanServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //if (req.getParameterMap().containsKey("id")) {
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<UserEntity> receivedUser = userService.findById(id);
        if (receivedUser.isPresent()) {
            UserEntity user = receivedUser.get();
                user.setBanned(true);
                userService.update(user);
                resp.sendRedirect(req.getHeader("referer"));

        }
        //}

    }
}
