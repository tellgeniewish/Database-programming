<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="beans.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
   User user = (User)session.getAttribute("userInfo");
%>
<html>
<head>
	<title>로그인 결과 화면</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
</head>
<body>
	로그인 성공!
	<table>
		<!-- User 객체의 각 필드를 출력 -->
	
		<tr><td>ID</td><td><%=user.getId()%></td></tr>	<!-- 또는 request.getParameter("id") --> 
		<tr><td>Password</td><td><%=user.getPassword()%></td></tr>	<!-- 또는 request.getParameter("password") -->    
		<tr><td>이름</td><td><%=user.getName()%></td></tr>    
	  
		<tr><td>나이</td><td>${userInfo.age}</td></tr>
		<tr><td>전화번호</td><td>${userInfo.phone}</td></tr>
	</table>
</body>
</html>