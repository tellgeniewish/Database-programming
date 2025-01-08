<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*, java.text.*" %>	<!-- 지시자(directive) -->
<%! 
    // 선언(declaration)
    private String time;
    private SimpleDateFormat dateFormat; 
    
    public void init() {			// life-cycle init method
    	dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
%>		
<%	// scriptlet
	time = dateFormat.format(new Date());
	String message = "Hello " + request.getParameter("name"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><%=message%></title>		<!-- 표현식(expression) -->
</head>
<body>
	<h1>helloWorld.jsp</h1>
	<b><%=message%></b><br><br>
	<% for (int i = 1; i <= 5; i++) { %>	
       <%= i %> : <%= time %><br/>
	<% } %>
</body>
</html>