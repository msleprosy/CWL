<%@ page import="com.epam.cwlhub.entities.snippet.Snippet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.epam.cwlhub.entities.user.UserEntity" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.epam.cwlhub.services.user.UserServiceImpl" %>
<%@ page import="static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table border="2px black" width="100%">
    <thead>
    <th style="display: none;">ID</th>
    <th>Name</th>
    <th>Creation date</th>
    <th>Modification date</th>
    <th>Tag</th>
    <th>Action</th>
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
                Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                                                                          .get(request.getSession().getId());
                Optional<UserEntity> receivedUser = UserServiceImpl.getInstance().findById(id);
                boolean isOwner = false;
                if (receivedUser.isPresent()) {
                    UserEntity user = receivedUser.get();
                    isOwner = snippet.getOwnerId() == user.getId();
                }
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
            <button formmethod="post" formaction="SnippetViewServlet">
                <a href='<%=request.getContextPath()+"/snippets?id=" + snippet.getId()%>'>Open</a>
            </button>
            <% if (isOwner) {%>
                <button>
                    <a href='<%=request.getContextPath()+"/delete?id=" + snippet.getId()%>'>Delete</a>
                </button>
            <%}%>
        </td>
    </tr>
    <%} %>
    </tbody>
    <%}%>
</table>
