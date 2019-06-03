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
    <h1>Upload CWL File</h1>
    <form method="post" action="<%=request.getContextPath()+ "/upload"%>" enctype="multipart/form-data">
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
</body>
</html>
