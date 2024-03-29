<%@ page import="com.epam.cwlhub.entities.snippet.Snippet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.epam.cwlhub.entities.user.UserEntity" %>
<%@ page import="com.epam.cwlhub.services.impl.UserServiceImpl" %>
<%@ page import= "com.epam.cwlhub.services.SnippetService" %>
<%@ page import= "com.epam.cwlhub.services.impl.SnippetServiceImpl" %>
<%@ page import="static com.epam.cwlhub.listeners.CWLAppServletContextListener.USER_SESSION_DATA" %>
<%@ page import="com.epam.cwlhub.dao.SnippetDao" %>
<%@ page import="com.epam.cwlhub.dao.impl.SnippetDaoImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table border="2px black" width="100%">
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
                Long id = ((Map<String, Long>) request.getServletContext().getAttribute(USER_SESSION_DATA))
                                                                          .get(request.getSession().getId());
                UserEntity user = UserServiceImpl.getInstance().findById(id);
                boolean isOwner  = snippet.getOwnerId() == user.getId();
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
            <form method="get" action="<%=request.getContextPath()+"/snippets"%>">
                <input type="hidden" name="id" value="<%=snippet.getId()%>">
                <input type="submit" value="Open">
            </form>
            <% if (isOwner) {%>
            <form method="post" action="<%=request.getContextPath()+"/delete"%>">
                <input type="hidden" name="id" value="<%=snippet.getId()%>">
                <input type="submit" value="Delete">
            </form>
            <%}%>
        </td>
    </tr>
    <%} %>

    </tbody>

    <%}%>
</table>
<table border="2px black" width="100%">
<tbody>
<%  long groupId = request.getParameterMap().containsKey("id") ? Long.parseLong(request.getParameter("id")) : 1;

    int snippetsCountForOnePage = SnippetServiceImpl.getInstance().numberOfSnippetsInGroup(groupId)/ SnippetDaoImpl.getSnippetNumberOnPage();
    if (snippetsCountForOnePage >= 1) {
%>
<tr>
<%
        for (int i = 1; i <= snippetsCountForOnePage; i++) {
%>
        <td>
            <a href='<%=request.getContextPath()+"?id="+groupId+"&page="+i%>'><%=i%></a>
        </td>
<%} %>
    </tr>
<%} %>
    </tbody>
    </table>
