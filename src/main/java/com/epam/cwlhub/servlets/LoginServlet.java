package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.storage.dbconnection.DBConnector;


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
        boolean hasError = false;
        String errorString = null;
        if (email == null || password == null || email.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = AUTHORIZATION_ERROR;
        } else {
            Connection conn = null;
            try {
                conn = DBConnector.getInstance().getDBConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                user = DBUtils.findUser(conn, email, password);
                if (user == null) {
                    hasError = true;
                    errorString = LOGIN_ERROR;
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
            RequestDispatcher dispatcher
                    = this.getServletContext().getRequestDispatcher(Endpoints.LOGIN_PAGE);

            dispatcher.forward(request, response);
        } else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);
            System.out.println("User logined");
        }
    }
}

