<%@ page import="com.epam.cwlhub.entities.user.UserEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.stream.Collectors" %>
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
