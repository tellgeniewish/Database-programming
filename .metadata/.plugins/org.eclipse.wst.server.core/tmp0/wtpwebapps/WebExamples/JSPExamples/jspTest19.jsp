<%@ page contentType="text/html;charset=utf-8"%>
<%
   request.setCharacterEncoding("utf-8");
   String name = "Dong-duk";
   String name2 = "Dongduk2";
%>
<html>
<body>
<h1>Include Tag Example</h1>

<% request.setAttribute("name2", name2); %>

<jsp:include page= "jspTest20.jsp"/>

include ActionTag의 Body입니다.
</body>
</html>