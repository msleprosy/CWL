<%@ page import="com.epam.cwlhub.entities.snippet.Snippet" %>
<%@ page import="java.util.List" %>
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
            <button formmethod="post" formaction="SnippetViewServlet">
                <a href='<%=request.getContextPath()+"/snippets?id=" + snippet.getId()%>'>Open</a>
            </button>
            <button>Download</button>
            <button id="show" <%if (false) {%> disabled <%}%> >
                <a href='<%=request.getContextPath()+"/delete?id=" + snippet.getId()%>'>Delete</a>
            </button>
        </td>
    </tr>
    <%}%>
    </tbody>
    <%}%>
</table>
