<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="beans.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Person, Dog</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	EL 사용: ${person.name} 또는 ${person["name"]}<br>
	Java code 사용:
	<%
		Person p =  (Person)request.getAttribute("person");
		String name = p.getName();
		out.print(name);
	%>
	<br><br>
	EL 사용: ${person.dog.name}<br>
	Java code 사용: 
	<%
		Dog myDog = p.getDog();
		out.print(myDog.getName());
	%>
</body>
</html>