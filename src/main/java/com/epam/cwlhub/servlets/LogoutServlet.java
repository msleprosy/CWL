package com.epam.cwlhub.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

import static com.epam.cwlhub.constants.Endpoints.*;
import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

@WebServlet(name = "LogoutServlet", urlPatterns = LOGOUT_URL)
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String USER = "user";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Map<String, Long> userSessionData = (Map<String, Long>) getServletContext().getAttribute(USER_SESSION_DATA);
            userSessionData.remove(session.getId());
            session.removeAttribute(USER);
            session.invalidate();
            deleteUserCookie(response);
            response.sendRedirect(LOGIN_URL);
        }
    }

    private void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie("JSESSIONID", null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
}
