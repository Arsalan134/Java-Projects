<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<link rel="stylesheet" href="style.css">
<title>Register</title>
</head>
<body>

	<form action="./register" method="post">
		<table>
			<tr>
				<td>Username:</td>
				<td><input type="text" name="username" placeholder="myname1" class="input" value=${ username} >
					${um}</td>
			</tr>

			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" class="input"/> ${pm}</td>
			</tr>

			<tr>
				<td>Confirm:</td>
				<td><input type="password" name="password2" class="input"/></td>
			</tr>
			<tr>
				<td>Full Name:</td>
				<td><input type="text" name="fullname" placeholder="John Smith" class="input" value=${ fullname}>
					${fm}</td>
			</tr>

			<tr>
				<td>Email:</td>
				<td><input type="email" name="email" placeholder="example@gmail.com" class="input" value=${ email}>
					${em}</td>
			</tr>

			<tr>
				<td><input type="submit" value="Register" class="button"></td>
				<td><input type="reset" class="button"></td>
		</table>
	</form>

	<a href="index.jsp"> <input type="button" value="Home"
		class="button"></a>

</body>
</html>