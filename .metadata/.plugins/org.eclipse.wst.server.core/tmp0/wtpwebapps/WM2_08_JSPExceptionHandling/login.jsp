<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" errorPage="error.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>User Home Page</title>
</head>
<body>
	<%
		String user = request.getParameter("id");
		String pwd = request.getParameter("password");

		if (user == null || "".equals(user) || pwd == null || "".equals(pwd))
			throw new ServletException("Mandatory Parameter missing");
	%>

	<%-- do some DB processing, not doing anything for simplicity --%>
	Hi
	<%=user%>
</body>
</html>