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
<!--joinSuccess.css-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/joinSuccess.css"/>
<link rel="shortcut icon" href="/mealbox/favicon.ico">
<title>회원가입 완료</title>
</head>
<body>
	<jsp:include page="../nav.jsp"/>

    <div id="container">
        <!--회원가입 헤더 부분-->
        <header>
            <h1 id="join_title" align="center">회원가입</h1>
            <div id="progress">
                <hr>
                <span id="progress_inputInfo">정보입력</span> 
                <div id="progress_sign">
                    <img src="${pageContext.request.contextPath}/images/arrow.png">
                </div>
                <span id="progress_complete">가입완료</span>
                <hr>
            </div>
        </header>

        <!--회원가입 메인 부분-->
        <main id="joinSuccess_main">
           <div>회원가입 완료</div>
           <div><span>${userName}</span>님의<br>
            회원가입이 완료되었습니다.</div>
        </main>
        <div id="joinSuccess_buttons" align="center" >
            <form id="loginButton" action="/mealbox/user/login/form" method="GET">
                <button class="style_button">로그인</button>
            </form>
            <form id="mainButton" action="/mealbox/product" method="GET">
            	<button class="style_button">메인으로</button>
            </form>
        </div>
    </div>
</body>
</html>