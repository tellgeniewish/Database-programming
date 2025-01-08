<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Array Loop 1</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<H2>Key Words:</H2>
	<UL>
	<c:forEach var="word" items="${words}">
		<LI>${word}
	</c:forEach>
	</UL>
</body>
</html>