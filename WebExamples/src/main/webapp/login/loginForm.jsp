<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>로그인 폼</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<form method="POST" action="<c:url value='/login'/>">
		아이디: <input type="text" name="id"><br>
		패스워드: <input type="password" name="password"><br><br>
		<input type="submit" value="로그인">
	</form>
</body>
</html>