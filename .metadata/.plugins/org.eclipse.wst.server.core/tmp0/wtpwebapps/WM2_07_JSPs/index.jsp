<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My First JSP page</title>
</head>
<body>


	<!-- This is HTML style comment (sent to the user side). -->
	<%@ page import="java.util.*"%>
	%>

	<p>
		Today's date is
		<%= new Date() %></p>

	<%-- This is JSP style comment (not sent to the user). --%>
	<p>
		<%
			String age = request.getParameter("age");
			String name = request.getParameter("name");
			out.println("Name is " + name + " and Age is " + age);
			out.println(add(Integer.parseInt(age), 5));
		%>
	</p>

	<%=session.getId()%>
	<br>

	<%!private int accessCount = 0; // instance var 

	int add(int a, int b) {
		return a + b;
	}%>
	Accesses to page since server reboot:
	<%=accessCount++%>


</body>
</html>