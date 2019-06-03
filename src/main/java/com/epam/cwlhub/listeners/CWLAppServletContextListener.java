package com.epam.cwlhub.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CWLAppServletContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<Long, HttpSession> userSessionData = new ConcurrentHashMap<>();
        ServletContext context = servletContextEvent.getServletContext();
        context.setAttribute("UserSessionData", userSessionData);
    }
}

