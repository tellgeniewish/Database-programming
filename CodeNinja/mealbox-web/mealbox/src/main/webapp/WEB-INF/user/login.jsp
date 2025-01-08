<!-- login.jsp 경로까지 모두 완료 -->
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!--폰트-->
	<link rel="preconnect" href="https://rsms.me/" />
	<link rel="stylesheet" href="https://rsms.me/inter/inter.css" />
	<!--login.css-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css"/>
	<link rel="shortcut icon" href="/mealbox/favicon.ico">
	<script>
	function login() {
		if (form.userId.value == "") {
			form.userId.focus();
			return false;
		} 
		if (form.password.value == "") {
			form.password.focus();
			return false;
		}		
		form.submit();
	}
	</script>
	<title>로그인</title>
</head>
<body>
    <jsp:include page="../nav.jsp"/>
	<div id="container">
	<!--로그인 헤더 부분-->
    <header>
     	<h1 id="login_title" align="center">로그인</h1><hr>
   	</header>
    <form method="POST"action="/mealbox/user/login" >
        <!--로그인 메인 부분-->
        <main>
            <input type="text" id="id" name="userid" placeholder="아이디를 입력해주세요">
            <input type="password" id="password" name="password" placeholder="비밀번호를 입력해주세요">
        </main>
        <div class="login_buttons">
        	<input type="submit" id="button_login" value="로그인" onClick="login()">
        </div>
    </form>
    <form method="GET" action="/mealbox/user/join/form">
    	<div class="login_buttons">
    		<button type="submit" id="button_join">회원가입</button>
    	</div>
    </form>
    </div>
</body>
</html>