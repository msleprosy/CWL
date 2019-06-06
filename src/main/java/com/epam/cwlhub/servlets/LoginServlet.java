package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.UserService;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

import static com.epam.cwlhub.constants.Endpoints.HOME_URL;
import static com.epam.cwlhub.constants.Endpoints.LOGIN_PAGE;
import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

public class LoginServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();
    private static final long serialVersionUID = 1L;
    private static final String ERROR = "errorString";
    private static final String USER = "user";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String ACCEPT_REMEMBERME = "Y";
    private static final String REMEMBERME_PARAMETER = "rememberMe";
    private static final String AUTHORIZATION_ERROR = "Required username and password!";
    private static final String LOGIN_ERROR = "User Name or password invalid";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher
                = this.getServletContext().getRequestDispatcher(LOGIN_PAGE);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter(EMAIL_PARAMETER).trim();
        String password = request.getParameter(PASSWORD_PARAMETER).trim();
        String rememberMeStr = request.getParameter(REMEMBERME_PARAMETER);
        boolean remember = ACCEPT_REMEMBERME.equals(rememberMeStr);
        String errorString = loginValidation(request);

        if (errorString != null) {
            UserEntity user = new UserEntity();
            user.setEmail(email);
            user.setPassword(password);
            request.setAttribute(ERROR, errorString);
            request.setAttribute(USER, user);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(Endpoints.LOGIN_PAGE);
            dispatcher.forward(request, response);
        } else {
            UserEntity userEntity = userService.findByEmail(email);
            Map<String, Long> userSessionData = (Map<String, Long>) getServletContext().getAttribute(USER_SESSION_DATA);
            userSessionData.put(request.getSession().getId(), userEntity.getId());
            if (remember) {
                HttpSession jsession = request.getSession();
                storeUserCookie(response, jsession);
            } else {
                deleteUserCookie(response);
            }
            response.sendRedirect(request.getContextPath() + HOME_URL);
        }
    }

    private String loginValidation(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAMETER).trim();
        String password = request.getParameter(PASSWORD_PARAMETER).trim();
        String errorString = null;
        UserEntity signInUser;
        if (email.length() == 0 || password.length() == 0) {
            errorString = AUTHORIZATION_ERROR;
        } else {
            signInUser = userService.findByEmail(email);
            if (signInUser != null) {
                errorString = LOGIN_ERROR;
            } else {
                if (!userService.checkUserPassword(password, signInUser)) {
                    errorString = LOGIN_ERROR;
                }
            }
        }
        return errorString;
    }

    private void storeUserCookie(HttpServletResponse response, HttpSession jsession) {
        System.out.println("Store user cookie");
        Cookie cookieUserName = new Cookie("JSESSIONID", jsession.getId());
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }

    private void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie("JSESSIONID", null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
}

