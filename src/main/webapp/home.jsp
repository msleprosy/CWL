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
                <jsp:include page="snippetListFromGroup.jsp"></jsp:include>
            </td>
            <td width="20%" height="60%">
                <button>Load file</button>
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