<%@ page import="com.epam.cwlhub.entities.snippet.Snippet" %>
<%@ page import="static com.epam.cwlhub.constants.Endpoints.ALL_GROUPS_URL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CWLHub</title>
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
                <a href="<%=request.getContextPath()+"/admin/userView"%>">Users</a>
                <a href="<%=request.getContextPath()+ALL_GROUPS_URL%>">Groups</a>
                <a href="<%=request.getContextPath()+"/admin/snippets"%>">Snippets</a>
                <a href="">Profile</a>
                <a href="<%=request.getContextPath()+"/logout"%>">Logout</a>
            </div>
        </div>
    </tr>

    <tr>
        <td width="60%" height="60%">
            <% if (request.getAttribute("snippet") != null) {
                Snippet snippet = (Snippet) request.getAttribute("snippet");
            %>
            <table align="center" border="2px solid black" width="60%">
                <tr>
                    <td>
                        <p><b>Name: </b><%= snippet.getName()%></p>
                        <p><b>Tags: </b> <%= snippet.getTag()%></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <textarea style="text-align: left" rows="30" cols="150" name="content" readonly>
                            <%= snippet.getContent()%>
                        </textarea>
                    </td>
                </tr>
            </table>

            <%} %>
        </td>
    </tr>

    <tr>
        <div style="background: #E0E0E0; text-align: center; padding: 5px; margin-top: 10px;">
            @Copyright BestCommandEpam
        </div>
    </tr>
</table>
</body>
</html>
