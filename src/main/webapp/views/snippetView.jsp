<%@ page import="com.epam.cwlhub.entities.snippet.Snippet" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.epam.cwlhub.entities.user.UserEntity" %>
<%@ page import="com.epam.cwlhub.services.impl.UserServiceImpl" %>
<%@ page import="static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA" %>
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
    <%@ page errorPage="/views/error.jsp" %>
    <table width="100%">
        <tr>
            <td colspan="3">
                <jsp:include page="/views/menu.jsp"></jsp:include>
            </td>
        </tr>

        <tr>
            <td width="20%" height="60%">
                <jsp:include page="/views/userGroups.jsp"></jsp:include>
            </td>
            <td width="60%" height="60%">
                <% if (request.getAttribute("snippet") != null) {
                    Snippet snippet = (Snippet) request.getAttribute("snippet");

                    Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                                                                              .get(request.getSession().getId());
                    UserEntity user = UserServiceImpl.getInstance().findById(id);
                    boolean isOwner = snippet.getOwnerId() == user.getId();
                %>
                <form method="post" action="<%=request.getContextPath()+"/update?id=" + snippet.getId()%>">
                    <table border="2px solid black" width="100%">
                        <tr>
                            <td>
                                <p><b>Name</b></p>
                                <input width="100%" name="name" value="<%= snippet.getName()%>" <%if (!isOwner) {%> readonly <%}%>/>
                            </td>
                            <%if (isOwner) {%>
                                <td width="20%" height="60%">
                                    <input type="submit" value="Save"/>
                                </td>
                            <%}%>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <p><b>Tags</b></p>
                                <input width="100%" name="tag" value="<%= snippet.getTag()%>" <%if (!isOwner) {%> readonly <%}%> />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <textarea style="text-align: left" cols="120" name="content"  <%if (!isOwner) {%> readonly <%}%> >
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
                <jsp:include page="/views/footer.jsp"></jsp:include>
            </td>
        </tr>
    </table>
</body>
</html>
