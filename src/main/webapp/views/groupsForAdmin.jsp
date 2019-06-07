<%@ page import="com.epam.cwlhub.entities.group.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA" %>
<%@ page import="static com.epam.cwlhub.constants.Endpoints.DELETE_GROUP_URL_ADMIN" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CWLHub Users for admin</title>
    <style>
        a {
            color: #000 !important;
            text-decoration: none;
        }
    </style>
</head>
<body>
<table width="100%">
    <tr>
        <div style="background: #E0E0E0; height: 40px; padding: 5px;">
            <div style="float: left">
                <h3><a href="<%=request.getContextPath()+"/home"%>">CWLHub</a></h3>
            </div>

            <div style="float: left; padding-left: 20px">
                <h3><a href="<%=request.getContextPath()+"/views/admin.jsp"%>">Admin page</a></h3>
            </div>
            <div style="float: right; padding: 10px; text-align: right;">
                <a href="<%=request.getContextPath()+"/views/usersForAdmin.jsp"%>">Users</a>
                <a href="<%=request.getContextPath()+"/views/groupsForAdmin.jsp"%>">Groups</a>
                <a href="<%=request.getContextPath()+"/views/groupsForAdmin.jsp"%>">Snippets</a>
                <a href="">Profile</a>
                <a href="<%=request.getContextPath()+"/logout"%>">Logout</a>
            </div>
        </div>
    </tr>
    <tr>
        <table border="2px black" style="margin: auto">
            <thead>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
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
            <% } else {

                for (Group group : groups) {
                    request.setAttribute("curGroupId", group.getId());
                    Long userId = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                            .get(request.getSession().getId());
            %>
            <tbody>
            <td>
                <%= group.getId()%>
            </td>
            <td>
                <%= group.getName()%>
            </td>
            <td>
                <%= group.getDescription()%>
            </td>
            <td align="center">
                <% if (group.getId() != 1) {%>
                <form method="post" action="<%=request.getContextPath()+DELETE_GROUP_URL_ADMIN%>">
                    <input type="hidden" name="id" value="<%=group.getId()%>">
                    <input type="submit" value="Delete">
                </form>
                <%
                        }
                    }
                %>
            </td>
            </tbody>
        </table>
        <%
                }
            }
        %>
    </tr>
    <tr>
        <div style="background: #E0E0E0; text-align: center; padding: 5px; margin-top: 10px;">
            @Copyright BestCommandEpam
        </div>
    </tr>
</table>
</body>
</html>