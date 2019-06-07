<%@ page import="com.epam.cwlhub.entities.snippet.Snippet" %>
<%@ page import="java.util.List" %>
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
                <a href="<%=request.getContextPath()+"/views/usersForAdmin.jsp"%>">Users</a>
                <a href="<%=request.getContextPath()+"/views/groupsForAdmin.jsp"%>">Groups</a>
                <a href="<%=request.getContextPath()+"/admin/snippets"%>">Snippets</a>
                <a href="">Profile</a>
                <a href="<%=request.getContextPath()+"/logout"%>">Logout</a>
            </div>
        </div>
    </tr>

    <tr>
        <table border="2px black" style="margin: auto">
            <thead>
                <th style="display: none;">ID</th>
                <th>Name</th>
                <th>Creation date</th>
                <th>Modification date</th>
                <th>Tag</th>
                <th colspan="2">Action</th>
            </thead>
            <%
                if (request.getAttribute("snippets") != null) {
                    List<Snippet> snippets = (List<Snippet>) request.getAttribute("snippets");

                    if (snippets.isEmpty()) {
            %>
            <tbody>
            <tr>
                <td colspan="6">There are no files to show</td>
            </tr>
            </tbody>
            <%      } else

                for (Snippet snippet : snippets) {

            %>
            <tbody>
            <tr>
                <td style="display: none;">
                    <%= snippet.getId()%>
                </td>
                <td>
                    <%= snippet.getName()%>
                </td>
                <td>
                    <%= snippet.getCreationDate()%>
                </td>
                <td>
                    <%= snippet.getModificationDate()%>
                </td>
                <td>
                    <%= snippet.getTag()%>
                </td>
            <td align="center">
                <form method="get" action="<%=request.getContextPath()+"/admin/snippets/snippet"%>">
                    <input type="hidden" name="id" value="<%=snippet.getId()%>">
                    <input type="submit" value="Open">
                </form>
                <form method="post" action="<%=request.getContextPath()+"/admin/snippets/delete"%>">
                    <input type="hidden" name="id" value="<%=snippet.getId()%>">
                    <input type="submit" value="Delete">
                </form>
                <%}%>
            </td>
            </tbody>
            <%}%>
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