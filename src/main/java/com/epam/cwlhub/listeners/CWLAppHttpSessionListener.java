package com.epam.cwlhub.listeners;

import com.epam.cwlhub.entities.user.UserEntity;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CWLAppHttpSessionListener implements HttpSessionListener {

    private Map<Long, HttpSession> userSessionData = new ConcurrentHashMap<>();

    public Map<Long, HttpSession> getUserSessionData() {
        return userSessionData;
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        Long userId = ((UserEntity) session.getAttribute("loginedUser")).getId();
        if (userId != null) {
            if (userSessionData.containsKey(userId)) {
                userSessionData.get(userId).invalidate();
            }
            userSessionData.put(userId, session);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        Long userId = ((UserEntity) session.getAttribute("loginedUser")).getId();
        if (userId != null) {
            userSessionData.remove(userId);
        }
    }
}

