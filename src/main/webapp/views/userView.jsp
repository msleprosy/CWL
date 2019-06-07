<%@ page import="com.epam.cwlhub.entities.user.UserEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
                <a href="<%=request.getContextPath()+"/views/userView.jsp"%>">Users</a>
                <a href="<%=request.getContextPath()+"/views/groupsForAdmin.jsp"%>">Groups</a>
                <a href="<%=request.getContextPath()+"/views/groupsForAdmin.jsp"%>">Snippets</a>
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
             <form method="post" action="<%=request.getContextPath()+"/banUsers"%>">
                 <input type="hidden" name="id" value="<%=user.getId()%>">
                 <input type="submit" value="Ban">
             </form>
             <form method="post" action="<%=request.getContextPath()+"/unbanUsers"%>">
                 <input type="hidden" name="id" value="<%=user.getId()%>">
                 <input type="submit" value="Unban">
             </form>
         </td>
     </tr>
 <%}%>
 <%}%>
</tbody>
</table>
</body>
</html>
