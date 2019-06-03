<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Message</title>
</head>
<body>
    <h3><%=request.getAttribute("Message")%></h3>
    <meta http-equiv="Refresh" content="3;url=UploadSnippet.jsp"/>
</body>
</html>
