<%@page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Employees List</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<br>
EmpNo / Name / Job / Salary<br>
----------------------------------------<br>
<c:forEach var="emp" items="${empList}">
   ${emp.empNo} / ${emp.empName} / ${emp.job} / ${emp.salary}<br>
</c:forEach> 
</body>
</html>