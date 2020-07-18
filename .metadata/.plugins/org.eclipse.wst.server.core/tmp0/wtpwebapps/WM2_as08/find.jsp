<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<link rel="stylesheet" href="style.css">
<title>Find</title>

<%@ page import="Models.User"%>

</head>
<body>

	<a href="index.jsp"> <input type="button" value="Home"
		class="button"></a>

	<form action="find.jsp" method="post">
		<select name="option">
			<option value="u_id">ID</option>
			<option value="username">Username</option>
			<option value="email">Email</option>
			<option value="firstname">Firstname</option>
			<option value="lastname">Lastname</option>
		</select> <input type="search" name="search" class="input"> <input
			type="submit" value="Search" class="button">
	</form>

	<%
		String option = request.getParameter("option");
		String search = request.getParameter("search");
	%>

	<%
		request.setAttribute("option", option);
		request.setAttribute("search", search);
		RequestDispatcher dispatcher = request.getRequestDispatcher("./search");
		dispatcher.include(request, response);
	%>

	<%
		ArrayList<User> users = null;
		users = (ArrayList<User>) request.getAttribute("users");
	%>

	<%
		if (users != null) {
	%>
	<table class="tablea">
		<tr>
			<td>ID</td>
			<td>Username</td>
			<td>Email</td>
			<td>Firstname</td>
			<td>Lastname</td>
		</tr>
		<br>

		<%
			for (int i = 0; i < users.size(); i++) {
		%>

		<tr>
			<td><%=users.get(i).getId()%></td>
			<td><%=users.get(i).getUsername()%></td>
			<td><%=users.get(i).getEmail()%></td>
			<td><%=users.get(i).getFirstname()%></td>
			<td><%=users.get(i).getLastname()%></td>
		</tr>

		<%
			}
		%>
	</table>

	<%
		}
	%>
</body>
</html>