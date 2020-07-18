<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>JSP EL Example Home</title>
</head>
<body>
	<%
		List<String> names = new ArrayList<String>();
		names.add("Anar");
		names.add("Tofig");
		pageContext.setAttribute("names", names);
	%>
	<strong>Simple . EL Example:</strong> ${requestScope.person}
	<br>
	<br>
	<strong>Simple . EL Example without scope:</strong> ${person}
	<br>
	<br>
	<strong>Simple [] Example:</strong> ${applicationScope["Admin"]}
	<br>
	<br>
	<strong>Multiples . EL Example:</strong> ${sessionScope.employee.address.address}
	<br>
	<br>
	<strong>List EL Example:</strong> ${names[1]}
	<br>
	<br>
	<strong>Header information EL Example:</strong> ${header["Accept-Encoding"]}
	<br>
	<br>
	<strong>Cookie EL Example:</strong> ${cookie["User"].value}
	<br>
	<br>
	<strong>pageContext EL Example:</strong> HTTP Method is	${pageContext.request.method}
	<br>
	<br>
	<strong>Context param EL Example:</strong> ${initParam.AppID}
	<br>
	<br>
	<strong>Arithmetic Operator EL Example:</strong> ${initParam.AppID + 200}
	<br>
	<br>
	<strong>Relational Operator EL Example:</strong> ${initParam.AppID < 200}
	<br>
	<br>

</body>
</html>