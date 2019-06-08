package com.epam.cwlhub.filters;

import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.services.GroupService;
import com.epam.cwlhub.services.impl.GroupServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.epam.cwlhub.constants.Endpoints.PAGE_FORBIDDEN;
import static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA;

@WebFilter(urlPatterns = "/groups")
public class GroupMemberFilter implements Filter {
    private final GroupService groupService = GroupServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        long userId = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                                                                  .get(request.getSession().getId());
        long groupId = Long.parseLong(request.getParameter("id"));
        boolean isAllowed = groupService.findUsersGroups(userId)
                                        .stream()
                                        .anyMatch(group -> group.getId() == groupId);

        if (isAllowed) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            request.getRequestDispatcher(PAGE_FORBIDDEN).forward(servletRequest, servletResponse);
        }
    }
}
