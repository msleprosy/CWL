package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.UserService;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.epam.cwlhub.constants.Endpoints.HOME_URL;
import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

public class LoginServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();
    private static final long serialVersionUID = 1L;
    private static final String ERROR = "errorString";
    private static final String USER = "user";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String AUTHORIZATION_ERROR = "Required username and password!";
    private static final String LOGIN_ERROR = "User Name or password invalid";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher
                = this.getServletContext().getRequestDispatcher(Endpoints.LOGIN_PAGE);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter(EMAIL_PARAMETER).trim();
        String password = request.getParameter(PASSWORD_PARAMETER).trim();
        UserEntity user = null;
        Optional<UserEntity> signInUser = Optional.empty();
        boolean hasError = false;
        String errorString = null;
        if (email == null || password == null || email.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = AUTHORIZATION_ERROR;
        } else {
            signInUser = userService.findByEmail(email);
            if (signInUser.equals(Optional.empty())) {
                hasError = true;
                errorString = LOGIN_ERROR;
            } else {
                if (!userService.checkUserPassword(password, signInUser.get())) {
                    hasError = true;
                    errorString = LOGIN_ERROR;
                }
            }

        }
        if (hasError) {
            user = new UserEntity();
            user.setEmail(email);
            user.setPassword(password);
            request.setAttribute(ERROR, errorString);
            request.setAttribute(USER, user);
            RequestDispatcher dispatcher
                    = this.getServletContext().getRequestDispatcher(Endpoints.LOGIN_PAGE);
            dispatcher.forward(request, response);
        } else {
            Map<String, Long> userSessionData = (Map<String, Long>) getServletContext().getAttribute(USER_SESSION_DATA);
            userSessionData.put(request.getSession().getId(), signInUser.get().getId());
            request.setAttribute("UserSessionData", userSessionData);
            response.sendRedirect(HOME_URL);
        }
    }
}

