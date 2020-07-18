<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Day JSP Page</title>
</head>
<body>
	<%
		if (Math.random() < 0.5) {
	%>
	Have a
	<b>nice</b> day!
	<%
		} else {
	%>
	Have a
	<b>lousy</b> day!
	<%
		}
	%>

</body>
</html>