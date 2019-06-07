package com.epam.cwlhub.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class CWLAppServletContextListener implements ServletContextListener{

    public static final String USER_SESSION_DATA = "UserSessionData";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<String, Long> userSessionData = new ConcurrentHashMap<>();
        ServletContext context = servletContextEvent.getServletContext();
        context.setAttribute(USER_SESSION_DATA, userSessionData);
    }
}

