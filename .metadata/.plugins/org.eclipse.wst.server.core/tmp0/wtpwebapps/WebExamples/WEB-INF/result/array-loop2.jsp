<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Array Loop 2</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<!-- results/array-loop2.jsp -->
	<UL>
		<c:forEach var="name" items="${names}">
			<LI>${name.firstName} ${name.lastName}
		</c:forEach>
	</UL>
	<H2>Comparing Apples and Oranges</H2>
	<TABLE BORDER="1">
		<TR>
			<TH>Year</TH>
			<TH>Apples Sold</TH>
			<TH>Oranges Sold</TH>
		</TR>
		<c:forEach var="row" items="${sales}">
			<TR>
				<c:forEach var="col" items="${row}">
					<TD>${col}</TD>
				</c:forEach>
			</TR>
		</c:forEach>
	</TABLE>
</body>
</html>