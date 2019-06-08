<%@ page import="com.epam.cwlhub.entities.user.UserEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="static com.epam.cwlhub.constants.Endpoints.ALL_ADMIN_GROUPS_URL" %>
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
                <h3><a href="<%=request.getContextPath()+"/admin"%>">Admin page</a></h3>
            </div>

            <div style="float: right; padding: 10px; text-align: right;">
                <a href="<%=request.getContextPath()+"/admin/viewUsers"%>">Users</a>
                <a href="<%=request.getContextPath()+ALL_ADMIN_GROUPS_URL%>">Groups</a>
                <a href="<%=request.getContextPath()+"/admin/snippets"%>">Snippets</a>
                <a href="">Profile</a>
                <a href="<%=request.getContextPath()+"/logout"%>">Logout</a>
            </div>
        </div>
    </tr>
</table>

<table border="2px black" style="margin: auto">
            <thead>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Ban status</th>
            <th>Ban</th>
            </thead>

<tbody>

 <%  if (request.getAttribute("user") != null) {
     List<UserEntity> users=(ArrayList<UserEntity>) request.getAttribute("user");
     for(UserEntity user: users){
 %>
     <tr>
         <td><%=user.getFirstName()%></td>
         <td><%=user.getLastName()%></td>
         <td><%=user.getEmail()%></td>
         <td><%=user.isBanned()%></td>
         <td align="center">
             <% if (!user.isBanned()) { %>
             <form method="post" action="<%=request.getContextPath()+"/admin/banUsers"%>">
                 <input type="hidden" name="id" value="<%=user.getId()%>">
                 <input type="submit" value="Ban">
             </form>
             <%} else {%>
             <form method="post" action="<%=request.getContextPath()+"/admin/unbanUsers"%>">
                 <input type="hidden" name="id" value="<%=user.getId()%>">
                 <input type="submit" value="Unban">
             </form>
             <%}%>
         </td>
     </tr>
     <%}%>
 <%}%>
</tbody>
</table>
<div style="background: #E0E0E0; text-align: center; padding: 5px; margin-top: 10px;">
    @Copyright BestCommandEpam
</div>
</body>
</html>
