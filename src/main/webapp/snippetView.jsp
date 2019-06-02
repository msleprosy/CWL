<%@ page import="com.epam.cwlhub.entities.snippet.Snippet" %>
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
            <td colspan="3">
                <jsp:include page="menu.jsp"></jsp:include>
            </td>
        </tr>

        <tr>
            <td width="20%" height="60%">
                <jsp:include page="userGroups.jsp"></jsp:include>
            </td>
            <td width="60%" height="60%">
                <% if (request.getAttribute("snippet") != null) {
                    Snippet snippet = (Snippet) request.getAttribute("snippet");
                %>
                <form method="POST" action="<%=request.getContextPath()+"/update?id=" + snippet.getId()%>">
                    <table border="2px solid black" width="100%">
                        <tr>
                            <td>
                                <p><b>Name</b></p>
                                <input width="100%" name="name" value="<%= snippet.getName()%>" <%if (false) {%> readonly <%}%>/>
                            </td>
                            <td width="20%" height="60%">
                                <input type="submit" <%if (false) {%> disabled <%}%> value="Save"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <p><b>Tags</b></p>
                                <input width="100%" name="tag" value="<%= snippet.getTag()%>" <%if (false) {%> readonly <%}%> />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <textarea style="text-align: left" cols="120" name="content"  <%if (false) {%> readonly <%}%> >
                                    <%= snippet.getContent()%>
                                </textarea>
                            </td>
                        </tr>
                    </table>
                </form>
                <%} %>
            </td>
        </tr>

        <tr>
            <td height="20%" colspan="3">
                <jsp:include page="footer.jsp"></jsp:include>
            </td>
        </tr>
    </table>
</body>
</html>
