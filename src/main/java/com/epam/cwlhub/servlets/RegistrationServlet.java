package com.epam.cwlhub.servlets;


import com.epam.cwlhub.constants.Endpoints;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.entities.user.UserType;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1;
    private UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String LASTNAME_PARAMETER = "lastName";
    private static final String FIRSTNAME_PARAMETER = "firstName";
    private static final String ERROR = "errorString";
    private static final String REGISTRATION_ERROR = "Need to fill all empty lines";
    private static final String EMAIL_ERROR = "User with that email already exists";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher
                = this.getServletContext().getRequestDispatcher(Endpoints.REGISTRATION_PAGE);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        UserEntity user = null;
        String errorString = registrationValidation(request, response);
        if (errorString != null) {
            user = new UserEntity();
            user.setEmail(email);
            user.setPassword(password);
            request.setAttribute(ERROR, errorString);
            RequestDispatcher dispatcher
                    = this.getServletContext().getRequestDispatcher(Endpoints.REGISTRATION_PAGE);
            dispatcher.forward(request, response);
        } else {
            userInstatiate(request);
            RequestDispatcher dispatcher
                    = this.getServletContext().getRequestDispatcher(Endpoints.USERDETAILS);
            dispatcher.forward(request, response);
        }

    }

    private UserEntity userInstatiate(HttpServletRequest request) {
        String firstName = request.getParameter(FIRSTNAME_PARAMETER);
        String lastName = request.getParameter(LASTNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        UserEntity user = registrationBuilder(firstName, lastName, email, password);
        return userService.insert(user);
    }

    private String registrationValidation(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter(FIRSTNAME_PARAMETER);
        String lastName = request.getParameter(LASTNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        UserEntity signUpUser;
        String errorString = null;
        if (email == null || password == null || firstName == null || lastName == null || email.length() == 0 || password.length() == 0
                || firstName.length() == 0 || lastName.length() == 0) {
            errorString = REGISTRATION_ERROR;
        } else {
            signUpUser = userService.findByEmail(email);
            if (signUpUser != null) {
                errorString = EMAIL_ERROR;
            }
        }
        return errorString;
    }

    private UserEntity registrationBuilder(String firstName, String lastName,
                                           String email, String password
    ) {
        UserEntity user = null;
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(UserType.SIMPLE_USER);
        user.setBanned(false);
        return user;
    }

}