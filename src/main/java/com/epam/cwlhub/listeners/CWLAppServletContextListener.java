package com.epam.cwlhub.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CWLAppServletContextListener implements ServletContextListener{

    public static final String USER_SESSION_DATA = "UserSessionData";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<String, Long> userSessionData = new ConcurrentHashMap<>();
        ServletContext context = servletContextEvent.getServletContext();
        context.setAttribute(USER_SESSION_DATA, userSessionData);
    }
}

