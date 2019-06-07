<%@ page import="com.epam.cwlhub.entities.group.Group" %>
<%@ page import="com.epam.cwlhub.entities.user.UserEntity" %>
<%@ page import="com.epam.cwlhub.services.impl.UserServiceImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA" %>
<%@ page import="com.epam.cwlhub.services.impl.GroupServiceImpl" %>
<%@ page import="static com.epam.cwlhub.constants.Endpoints.DELETE_GROUP_URL_USER" %>
<%@ page import="static com.epam.cwlhub.constants.Endpoints.JOIN_GROUP_URL" %>
<%@ page import="static com.epam.cwlhub.constants.Endpoints.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/views/menu.jsp"></jsp:include>
<table border="0" width="100%">
    <col width="75%">
    <tr>
        <td>
            <table border="2px black" width="100%">
                <thead>
                <th style="display: none;">ID</th>
                <th>Name</th>
                <th>Description</th>
                <th style="display: none;">Creator_ID</th>
                <th>Action</th>
                </thead>
                <%
                    if (request.getAttribute("groups") != null) {
                        List<Group> groups = (List<Group>) request.getAttribute("groups");
                        if (groups.isEmpty()) {
                %>
                <tbody>
                <tr>
                    <td colspan="6">There are no groups to show</td>
                </tr>
                </tbody>
                <% } else
                    for (Group group : groups) {
                        Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                                .get(request.getSession().getId());
                        UserEntity receivedUser = UserServiceImpl.getInstance().findById(id);
                        boolean isOwner = false;
                        if (receivedUser != null) {
                            isOwner = group.getCreatorId() == receivedUser.getId();
                        }
                %>
                <tbody>
                <tr>
                    <td style="display: none;">
                        <%= group.getId()%>
                    </td>
                    <td>
                        <%= group.getName()%>
                    </td>
                    <td>
                        <%= group.getDescription()%>
                    </td>
                    <td style="display: none;">
                        <%= group.getCreatorId()%>
                    </td>
                    <td align="center">
                        <% if (!isOwner && GroupServiceImpl.getInstance().checkMembership(id, group.getId()) && group.getId() != 1) {%>
                        <form method="post" action="<%=request.getContextPath()+LEAVE_GROUP_URL%>">
                            <input type="hidden" name="id" value="<%=group.getId()%>">
                            <input type="submit" value="Leave">
                        </form>
                        <%}%>
                        <% if (isOwner && group.getId() != 1) {%>
                        <form method="post" action="<%=request.getContextPath()+DELETE_GROUP_URL_USER%>">
                            <input type="hidden" name="id" value="<%=group.getId()%>">
                            <input type="submit" value="Delete">
                        </form>
                        <%}%>
                        <% if (!isOwner && !GroupServiceImpl.getInstance().checkMembership(id, group.getId()) && group.getId() != 1) {%>
                        <form method="post" action="<%=request.getContextPath()+JOIN_GROUP_URL%>">
                            <input type="hidden" name="id" value="<%=group.getId()%>">
                            <input type="submit" value="Join">
                        </form>
                        <%}%>
                    </td>
                </tr>
                <%} %>
                </tbody>
                <%}%>
            </table>
        </td>
        <td>
            <div>
                <h3>Create new group</h3>
                <div style="color: red;">${errorMessage}</div>
                <form method="post" action="<%=request.getContextPath()+CREATE_GROUP_URL%>">
                    <table border="0">
                        <tr>
                            <td>Group name:</td>
                            <td><input type="text" name="name"/></td>
                        </tr>
                        <tr>
                            <td>Group description:</td>
                            <td><input type="text" name="description"/></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="Save">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </td>
    </tr>
</table>