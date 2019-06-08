package com.epam.cwlhub.filters;

import com.epam.cwlhub.entities.user.UserEntity;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

import static com.epam.cwlhub.constants.Endpoints.*;

@WebFilter(urlPatterns = "/*")
public class AuthentificationFilter implements Filter {
    private static final String USER = "user";
    private static final String EXCLUDED_PAGES = "/(?!(loginView.jsp|login|logout))\\w.*";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();

        if (Pattern.matches(EXCLUDED_PAGES, requestURI)) {
            HttpSession httpSession = httpServletRequest.getSession(false);
            UserEntity user = (UserEntity) httpSession.getAttribute(USER);

            boolean isLoggedIn = httpSession.getId() != null && user != null;

            if (!isLoggedIn || (user.isBanned())) {
                ((HttpServletResponse) servletResponse).sendRedirect(LOGIN_URL);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            httpServletRequest.getRequestDispatcher(requestURI).forward(servletRequest, servletResponse);
        }
    }
}
