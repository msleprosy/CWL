package com.epam.cwlhub.servlets;

import com.epam.cwlhub.constants.Endpoints;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import static com.epam.cwlhub.constants.Endpoints.LOGOUT;

@WebServlet(name = "LogoutServlet", urlPatterns = LOGOUT)
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
    private static final String USER = "user";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(USER);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Endpoints.LOGIN_PAGE);
            dispatcher.forward(request, response);
            deleteUserCookie(response);
        }
    }
    private void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
}

