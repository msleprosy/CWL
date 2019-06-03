<%@ page import="java.util.Map" %>
<%@ page import="com.epam.cwlhub.entities.user.UserEntity" %>
<%@ page import="com.epam.cwlhub.entities.user.UserType" %>
<%@ page import="com.epam.cwlhub.services.user.UserServiceImpl" %>
<%@ page import="java.util.Optional" %>
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
            Long id = ((Map<String, Long>) request.getServletContext().getAttribute("UserSessionData")).get(request.getSession().getId());
            Optional<UserEntity> receivedUser = UserServiceImpl.getInstance().findById(id);
            if (receivedUser.isPresent()) {
                UserEntity user = receivedUser.get();
                if (UserType.ADMINISTRATOR.equals(user.getUserType())) {
        %>
        <a href="">Users</a>
        <a href="">Groups</a>
        <%} }%>
        <a href="">Profile</a>
        <a href="">Logout</a>
    </div>
</div>

