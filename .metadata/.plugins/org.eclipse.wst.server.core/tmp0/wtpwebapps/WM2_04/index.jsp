<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My first JSP Page</title>
</head>
<body>

<!-- This is a comment -->
<%@ page import = "java.util.*" %>

	<p>
		Today's date is
		<%=new Date()%>
	</p>
	
	<%-- This a JSp style comment (not sent to user) --%>

	<p>
		<%
			String age = request.getParameter("age");
			String name = request.getParameter("name");
		
			out.println("Age is " + age);
			out.println(" Name is " + name);
			%>
	</p>
	
	
	<%= session.getId() %>

</body>
</html>

