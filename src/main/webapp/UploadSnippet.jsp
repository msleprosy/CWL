name, owner_id, creation_date, modification_date, content, tag, group_id)

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
<center>
    <h1>CWL Upload to Database</h1>
    <form method="post" action="uploadSnippetServlet" enctype="multipart/form-data">
        <table border="0">
            <tr>
                <td>File Name: </td>
                <td><input type="text" name="firstName" size="50"/></td>
            </tr>
            <tr>
                <td>Last Name: </td>
                <td><input type="text" name="lastName" size="50"/></td>
            </tr>
            <tr>
                <td>File</td>: </td>
                <td><input type="file" name="cwl" size="50"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Save">
                </td>
            </tr>
        </table>
    </form>
</center>
</body>
</html>
