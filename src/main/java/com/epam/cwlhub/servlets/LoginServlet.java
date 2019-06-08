package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.UserService;
import com.epam.cwlhub.services.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

import static com.epam.cwlhub.constants.Endpoints.*;
import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

@WebServlet(name = "LoginServlet", urlPatterns = LOGIN_URL)
public class LoginServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();
    private static final long serialVersionUID = 1L;
    private static final String USER = "user";
    private static final String ERROR = "errorString";
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter(EMAIL_PARAMETER).trim();
        String rememberMeStr = request.getParameter(REMEMBERME_PARAMETER);
        boolean remember = ACCEPT_REMEMBERME.equals(rememberMeStr);
        String errorString = loginValidation(request);

        if (errorString != null) {
            request.setAttribute(ERROR, errorString);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(Endpoints.LOGIN_PAGE);
            dispatcher.forward(request, response);
        } else {
            UserEntity userEntity = userService.findByEmail(email);
            request.getSession().setAttribute(USER, userEntity);
            Map<String, Long> userSessionData = (Map<String, Long>) getServletContext().getAttribute(USER_SESSION_DATA);
            userSessionData.put(request.getSession().getId(), userEntity.getId());
            if (remember) {
                HttpSession jsession = request.getSession();
                storeUserCookie(response, jsession);
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
            if (signInUser == null) {
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
        Cookie cookieUserName = new Cookie("JSESSIONID", jsession.getId());
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }
}

