<%@ page contentType="text/html; charset=utf-8" %>
<jsp:useBean id="test" class="beans.SimpleBean" scope="page" />
<jsp:setProperty name="test" property="message" value="JavaBeans!!!" />

<html>
<body>
<h1>간단한 빈 프로그래밍</h1>
<br>
Message: <jsp:getProperty name="test" property="message" />
</body>
</html>
