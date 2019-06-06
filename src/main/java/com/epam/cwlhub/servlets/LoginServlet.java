package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.exceptions.unchecked.UserException;
import com.epam.cwlhub.services.UserService;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
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
    private static final String ACCEPT_REMEMBERME = "Y";
    private static final String REMEMBERME_PARAMETER = "rememberMe";
    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
    private static final String ATT_NAME_PASSWORD = "ATTRIBUTE_FOR_STORE_PASSWORD_IN_COOKIE";
    private static final String ATT_NAME_REMEMBERME = "ATTRIBUTE_FOR_STORE_REMEMBERME_IN_COOKIE";
    private static final String AUTHORIZATION_ERROR = "Required username and password!";
    private static final String LOGIN_ERROR = "User Name or password invalid";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
                UserEntity userEntity = userService.findByEmail(email).orElseThrow(() -> new UserException("cant find by email"));
                Map<String, Long> userSessionData = (Map<String, Long>) getServletContext().getAttribute(USER_SESSION_DATA);
                userSessionData.put(request.getSession().getId(), userEntity.getId());
                Long id = userSessionData
                        .get(request.getSession().getId());
                Optional<UserEntity> receivedUser = UserServiceImpl.getInstance().findById(id);
                UserEntity cookieUser = receivedUser.get();
                if (remember) {
                    storeUserCookie(response,cookieUser);
                } else {
                    deleteUserCookie(response);
                }
                response.sendRedirect(request.getContextPath() + HOME_URL);
            }
        }



    private String loginValidation(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAMETER).trim();
        String password = request.getParameter(PASSWORD_PARAMETER).trim();
        Optional<UserEntity> signInUser;
        String errorString = null;
        if (email.length() == 0 || password.length() == 0) {
            errorString = AUTHORIZATION_ERROR;
        } else {
            signInUser = userService.findByEmail(email);
            if (!signInUser.isPresent()) {
                errorString = LOGIN_ERROR;
            } else {
                if (!userService.checkUserPassword(password, signInUser.get())) {
                    errorString = LOGIN_ERROR;
                }
            }
        }
        return errorString;
    }

    private void storeUserCookie(HttpServletResponse response, UserEntity cookieUser) {
        System.out.println("Store user cookie");
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, cookieUser.getEmail());
        Cookie cookiePassword = new Cookie(ATT_NAME_PASSWORD,  cookieUser.getPassword());
        cookieUserName.setMaxAge(24 * 60 * 60);
        cookiePassword.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
        response.addCookie(cookiePassword);

    }

    private void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        Cookie cookiePassword = new Cookie(ATT_NAME_PASSWORD, null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
        response.addCookie(cookiePassword);

    }
}




