<%@ page import="com.epam.cwlhub.entities.group.Group" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table border="2px black" width="100%">
    <thead>
        <th style="display: none;">ID</th>
        <th>Groups</th>
    </thead>
    <tbody>
        <%
            if (request.getAttribute("userGroups") != null) {
                List<Group> userGroups = (List<Group>) request.getAttribute("userGroups");

                for (Group group : userGroups) {
        %>
        <tr>
            <td style="display: none;">
                <%= group.getId()%>
            </td>
            <td>
                <a href='<%=request.getContextPath()+"/groups?id=" + group.getId()%>'><%= group.getName()%></a>
            </td>
        </tr>
        <%} %>
    </tbody>
    <%} %>
</table>