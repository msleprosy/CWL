package com.epam.cwlhub.filters;

import com.epam.cwlhub.constants.Endpoints;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.entities.user.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {
    private static final String ERROR_MESSAGE = "You don't have the necessary permissions to visit this page.";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);
        if (session != null) {
            UserType currentRole = ((UserEntity) session.getAttribute("user")).getUserType();
            if (currentRole == UserType.ADMINISTRATOR) {
                chain.doFilter(req, resp);
                return;
            }
        }

        req.setAttribute("errorMsg", ERROR_MESSAGE);
        RequestDispatcher rd = req.getRequestDispatcher(Endpoints.ERROR_PAGE);
        rd.forward(req, resp);
    }
}
