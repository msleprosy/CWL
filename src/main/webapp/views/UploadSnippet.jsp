<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>CWL Upload</title>
</head>
<body>
<jsp:include page="/views/menu.jsp"></jsp:include>
    <h1>Upload CWL File</h1>
    <form method="post" action="<%=request.getContextPath()+"/upload?group_id=" + request.getParameter("group_id")%>" enctype="multipart/form-data">
        <div style="color: #FF0000;">${errorMessage}</div><br>
        <table border="0">
            <tr>
                <td>File Name: </td>
                <td><input type="text" name="fileName" size="50"/></td>
            </tr>
            <tr>
                <td>Tags: </td>
                <td><input type="text" name="tags" size="50"/></td>
            </tr>
            <tr>
                <td>File: </td>
                <td><input type="file" name="cwl" size="50"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Save">
                </td>
            </tr>
        </table>
    </form>
<jsp:include page="/views/footer.jsp"></jsp:include>
</body>
</html>
