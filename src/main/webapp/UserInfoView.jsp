<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Info</title>
</head>
<body>

<jsp:include page="views/menu.jsp"></jsp:include>

<h3>Email: ${user.email}</h3>

First Name: <b>${user.firstName}</b>
<br />
Last Name: ${user.lastName } <br />

<tr>
    <td colspan="2">
        <input type="submit" value="Edit data"/>
    </td>
</tr>

<jsp:include page="views/footer.jsp"></jsp:include>

</body>
</html>