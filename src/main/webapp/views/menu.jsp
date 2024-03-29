<%@ page import="java.util.Map" %>
<%@ page import="com.epam.cwlhub.entities.user.UserEntity" %>
<%@ page import="com.epam.cwlhub.entities.user.UserType" %>
<%@ page import="com.epam.cwlhub.services.impl.UserServiceImpl" %>
<%@ page import="static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA" %>
<%@ page import="static com.epam.cwlhub.constants.Endpoints.ALL_GROUPS_URL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    a {
        color: #000 !important;
        text-decoration: none;
    }
</style>

<div style="background: #E0E0E0; height: 40px; padding: 5px;">
    <div style="float: left">
        <h3><a href="<%=request.getContextPath()+"/home"%>">CWLHub</a></h3>
    </div>

    <div style="float: right; padding: 10px; text-align: right;">
        <%
            Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                    .get(request.getSession().getId());
            UserEntity receivedUser = UserServiceImpl.getInstance().findById(id);
            if (receivedUser != null) {
                if (UserType.ADMINISTRATOR.equals(receivedUser.getUserType())) {
        %>
        <a href="<%=request.getContextPath()+"/admin"%>">Admin</a>
        <%} }%>
        <a href="<%=request.getContextPath() + ALL_GROUPS_URL%>">Groups</a>
        <a href="${pageContext.request.contextPath}/userInfo">Profile</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>

