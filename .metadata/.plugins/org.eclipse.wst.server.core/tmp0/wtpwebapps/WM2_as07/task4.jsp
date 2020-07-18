<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="errorMissingParametrs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%-- ${ stateBean.stateName } --%>

	<form action="./task4">
		<br> <input type="text" name="state"
			value=${ stateBean.stateName }> <br> <input
			type="submit"> <input type="reset">
	</form>

</body>
</html>