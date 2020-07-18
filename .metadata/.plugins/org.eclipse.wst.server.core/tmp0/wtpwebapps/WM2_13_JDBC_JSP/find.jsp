<%@ page contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/styles.css">
<title>Find an Employee</title>
</head>
<body>

	<p>Using this form you may retrieve an employee:</p>

	<form action="find-emp">
		Employee ID <input type="number" name="id"> ${msg.text}<br> 
		<input type="submit" value="Search">
	</form>
	<br>
	<br>

	<table CLASS="TITLE">
		<tr>
			<th>Search Results</th>
		</tr>
	</table>
	<br>

	<table border="1">
		<tr>
			<td>${emp.employeeID}</td>
			<td>${emp.firstName}</td>
			<td>${emp.lastName}</td>
			<td>${emp.position}</td>
			<td>${emp.salary}</td>
		</tr>
	</table>

</body>
</html>