<%@ page import="static com.epam.cwlhub.constants.Endpoints.ALL_ADMIN_GROUPS_URL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CWLHub Admin page</title>
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
                    <a href="<%=request.getContextPath()+"/userInfo"%>">Profile</a>
                    <a href="<%=request.getContextPath()+"/logout"%>">Logout</a>
                </div>
            </div>
        </tr>

        <tr>
            <div style="text-align:center">
                <h1>Welcome to admin panel!</h1>
            </div>
        </tr>

        <tr>
            <div style="background: #E0E0E0; text-align: center; padding: 5px; margin-top: 10px;">
                @Copyright BestCommandEpam
            </div>
        </tr>
    </table>
</body>
</html>
