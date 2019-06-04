package com.epam.cwlhub.servlets;


import com.epam.cwlhub.constants.Endpoints;
import com.epam.cwlhub.dao.impl.UserDaoImpl;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.entities.user.UserType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1;
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String LASTNAME_PARAMETER = "lastName";
    private static final String FIRSTNAME_PARAMETER = "firstName";
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter(FIRSTNAME_PARAMETER);
        String lastName = request.getParameter(LASTNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);

        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(UserType.SIMPLE_USER);
        user.setBanned(false);
        userDao.insert(user);
        response.sendRedirect(Endpoints.USERDETAILS);
    }
}