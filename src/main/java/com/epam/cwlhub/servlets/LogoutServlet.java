package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String USER = "user";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(USER);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Endpoints.LOGIN_PAGE);
            dispatcher.forward(request, response);
        }
    }
}

