<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Page</title>

<style>
* {
	font-size: 20px;
	font-family: Helvica Neue;
	background-color: #FDAFAE;
} </style>

</head>
<body>

	<font color="red"> Missing parameter </font>

	<br> Hi There, error code is
	<%=response.getStatus()%><br> Please go to
	<a href="index.jsp">home page</a>

</body>
</html>