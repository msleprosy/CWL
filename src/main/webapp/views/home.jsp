<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<%
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                sessionID = cookie.getValue();
            }
        }
    }
%>
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
                <jsp:include page="/views/snippetListFromGroup.jsp"></jsp:include>
            </td>
            <td width="20%" height="60%">
                <form method="get" action="<%=request.getContextPath()+"/upload"%>">
                    <input type="hidden" name="group_id" value="1">
                    <input type="submit" value="Load file">
                </form>
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