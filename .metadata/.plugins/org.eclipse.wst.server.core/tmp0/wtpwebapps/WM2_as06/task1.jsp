<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Task 1</title>
</head>
<body>

	<%@ page import="java.util.*"%>

	<%!String randomColor1() {
		String colors[] = { "White", "Red", "Green", "Blue", "Yellow", "Cyan", "Magenta", "Pink", "Purple", "Black" };
		Random r = new Random();
		return colors[r.nextInt(colors.length)];
	}%>
	
	<style> * {background-color: <%= randomColor1()%> }</style> 
	
</body>
</html>