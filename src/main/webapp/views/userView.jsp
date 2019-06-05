<%--
  Created by IntelliJ IDEA.
  User: veronika
  Date: 04.06.2019
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<h1>Hello ${name}</h1>
</body>
</html>--%>

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
            <%--<div style="float: left">
                <h3><a href="<%=request.getContextPath()+"/home"%>">CWLHub</a></h3>
            </div>

            <div style="float: left; padding-left: 20px">
                <h3><a href="<%=request.getContextPath()+"/admin"%>">Admin page</a></h3>
            </div>

            <div style="float: right; padding: 10px; text-align: right;">
                <a href="<%=request.getContextPath()+"/views/usersForAdmin.jsp"%>">Users</a>
                <a href="<%=request.getContextPath()+"/views/groupsForAdmin.jsp"%>">Groups</a>
                <a href="<%=request.getContextPath()+"/views/groupsForAdmin.jsp"%>">Snippets</a>
                <a href="">Profile</a>
                <a href="<%=request.getContextPath()+"/logout"%>">Logout</a>
            </div>--%>
        </div>
    </tr>

    <tr>
        <table border="2px black" style="margin: auto">
            <thead>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Ban</th>
            <th>Ban status</th>
            </thead>
            <tbody>
            <td>

            </td>
            <td>

            </td>
            <td>

            </td>
            <td>

            </td>
            <td align="center">
               <%-- <% if (true) { %>
                <button>
                    <a href="">Ban</a>
                </button>
                <%} else { %>
                <button>
                    <a href="">Unban</a>
                </button>
                <%} %>--%>
                   <button type="submit" name="button" value="ban">Ban</button>
                   <button type="submit" name="button" value="unban">Unban</button>
            </td>

            </td>
            <td>

            </tbody>
        </table>
    </tr>

    <tr>
        <div style="background: #E0E0E0; text-align: center; padding: 5px; margin-top: 10px;">
            @Copyright BestCommandEpam
        </div>
    </tr>
</table>
</body>
</html>
