package com.epam.cwlhub.servlets.user;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.exceptions.unchecked.UserException;
import com.epam.cwlhub.services.UserService;
import com.epam.cwlhub.services.impl.UserServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class UserServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //req.setAttribute("name", "userServlet");
        try {
            List<UserEntity> users = userService.findAll();
            if (!users.isEmpty()) {
                req.setAttribute("user", users);
            }
        req.getRequestDispatcher("/views/userView.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new UserException("Can't display all users", e);
        }

    }
}

/*    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if (req.getParameterMap().containsKey("id")) {
                Long id = Long.parseLong(req.getParameter("id"));
                Optional<UserEntity> receivedUser = userService.findById(id);
                if (receivedUser.isPresent()) {
                    UserEntity user = receivedUser.get();
                    if (req.getParameter("button").equals("true")) {
                    user.setBanned(true);
                }
                    else {
                        user.setBanned(false);
                    }
                }
            }

    }
}*/
