<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<jsp:include page="_header.jsp"></jsp:include>

<h3>Login Page</h3>
<p style="color: red;">${errorString}</p>

<form method="POST" action="${pageContext.request.contextPath}/login">
    <table border="0">
        <tr>
            <td>Email</td>
            <td><input type="email" name="email" value="${user.email}"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" value="${user.password}"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Login"/>
                <a href="${pageContext.request.contextPath}/">Cancel</a>
            </td>
        </tr>
    </table>
</form>

<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>