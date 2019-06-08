<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<jsp:include page="_menu.jsp"></jsp:include>

<h3>Login Page</h3>
<p style="color: red;">${errorString}</p>

<form method="POST" action="${pageContext.request.contextPath}/login">

    <%
        Cookie[] cookies=request.getCookies();
        String email = "", password = "",rememberVal="";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE")) {
                    email = cookie.getValue();
                }
                if(cookie.getName().equals("ATTRIBUTE_FOR_STORE_PASSWORD_IN_COOKIE")){
                    password = cookie.getValue();
                }
            }

        }
    %>
    <table border="0">
        <tr>
            <td>Email</td>
            <td><input type="email" name="email" value="<%=email%>"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" value="<%=password%>"/></td>
        </tr>
        <tr>
        <tr>
            <td>Remember me</td>
            <td><input type="checkbox" name="rememberMe" value="Y"/></td>
        </tr>
            <td colspan="2">
                <input type="submit" value="Login"/>
                <a href="${pageContext.request.contextPath}/">Cancel</a>
            </td>
        </tr>
    </table>
</form>
</form>
<form action="/userregister.jsp" method="post">
    <button type="submit">Register</button>
</form>

<p style="color:blue;">There's no such thing like a free lunch</p>

<jsp:include page="views/footer.jsp"></jsp:include>
</body>
</html>