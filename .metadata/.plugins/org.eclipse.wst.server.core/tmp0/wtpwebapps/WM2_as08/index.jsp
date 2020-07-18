<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<link rel="stylesheet" href="style.css">
<title>Login</title>
</head>
<body>


	<form action="./login" method="post" id="formID">
		<table>
			<tr>
				<td>Username:</td>
				<td><input type="text" name="name" class="input" value=${ name}>
					${ message }</td>
			</tr>

			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" class="input">
					${ pmessage }</td>
			</tr>

		</table>
		<input type="submit" value="Login" id="submitButton" class="button">
		<input type="reset" class="button">
	</form>

	<a href="register.jsp"><input type="button" value="Register"
		class="button" id="registerButton"></a>

	<a href="find.jsp"> <input type="button" value="Search"
		class="button">
	</a>


<%-- 	<%
		String username = request.getParameter("name");
		String password = request.getParameter("password");
	%>

	<%
		request.setAttribute("name", username);
		request.setAttribute("password", password);
		RequestDispatcher dispatcher = request.getRequestDispatcher("./login");
		dispatcher.forward(request, response);
	%> --%>

</body>
</html>