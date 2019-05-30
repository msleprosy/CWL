package com.epam.cwlhub.servlets;

import com.epam.cwlhub.entities.user.UserEntity;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ACCEPT_REMEMBERME = "Y";
    private static final String ERROR = "errorString";
    private static final String USER = "user";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher
                = this.getServletContext().getRequestDispatcher(UrlAttrs.LOGIN_URL);

        dispatcher.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter(UserAttrs.EMAIL_PARAMETER).trim();
        String password = request.getParameter(UserAttrs.PASSWORD_PARAMETER).trim();
        String rememberMeStr = request.getParameter(UserAttrs.REMEMBERME_PARAMETER);
        boolean remember = ACCEPT_REMEMBERME.equals(rememberMeStr);

        UserEntity user = null;
        boolean hasError = false;
        String errorString = null;
        if (email == null || password == null || email.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = UserAttrs.AUTHORIZATION_ERROR;
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {
                user = DBUtils.findUser(conn, email, password);
                if (user == null) {
                    hasError = true;
                    errorString = UserAttrs.LOGIN_ERROR;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        if (hasError) {
            user = new UserEntity();
            user.setEmail(email);
            user.setPassword(password);
            request.setAttribute(ERROR, errorString);
            request.setAttribute(USER, user);
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher(UrlAttrs.LOGIN_URL);

            dispatcher.forward(request, response);
        } else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);

            if (remember) {
                MyUtils.storeUserCookie(response, user);
            } else {
                MyUtils.deleteUserCookie(response);
            }
            response.sendRedirect(request.getContextPath() + UrlAttrs.USERINFO_URL);
        }
    }
}

