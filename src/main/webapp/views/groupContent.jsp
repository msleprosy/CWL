<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="/views/error.jsp" %>
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
                <input type="hidden" name="group_id" value="<%=request.getParameter("id")%>">
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