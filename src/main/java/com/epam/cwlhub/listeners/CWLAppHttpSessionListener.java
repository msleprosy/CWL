package com.epam.cwlhub.listeners;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CWLAppHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        Map<String, Long> userSessionData = new ConcurrentHashMap<>();
        session.setAttribute("UserSessionData", userSessionData);

    }
}
