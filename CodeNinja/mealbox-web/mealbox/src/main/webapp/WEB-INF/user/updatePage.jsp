<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--폰트-->
    <link rel="preconnect" href="https://rsms.me/" />
    <link rel="stylesheet" href="https://rsms.me/inter/inter.css" />
    <!--join.css-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/updatePage.css"/>
    <link rel="shortcut icon" href="/mealbox/favicon.ico">
    <!--join.js-->
    <script type="text/javascript">
    function checkValidForm(){
    	var form = document.getElementById("container");
    	if(form.password.value == ""){
    		document.getElementById("text_password").style.color = "red";
    		form.password.focus();
    		return false;
    	}
    	
    	if(form.check_password.value != form.password.value){
    		document.getElementById("text_checkPassword").style.color = "red";
    		form.check_password.focus();
    		return false;
    	}
    	if(form.name.value == ""){
    		document.getElementById("text_name").style.color = "red";
    		form.name.focus();
    		return false;
    	}
    	
    	const phonePattern1 = /^[0-9]{3}$/; // 앞자리: 숫자 3자리
        const phonePattern2 = /^[0-9]{4}$/; // 중간자리: 숫자 4자리
        const phonePattern3 = /^[0-9]{4}$/; // 뒷자리: 숫자 4자리
        
    	if(!phonePattern1.test(form.phone_part1.value)){
    		document.getElementById("text_phone").style.color = "red";
    		form.phone_part1.focus();
    		return false;
    	}
    	if(!phonePattern2.test(form.phone_part2.value)){
    		document.getElementById("text_phone").style.color = "red";
    		form.phone_part2.focus();
    		return false;
    	}
    	if(!phonePattern3.test(form.phone_part3.value)){
    		document.getElementById("text_phone").style.color = "red";
    		form.phone_part3.focus();
    		return false;
    	}
    	if(form.email_id.value == ""){
    		document.getElementById("text_email").style.color = "red";
    		form.email_id.focus();
    		return false;
    	}
    	if(form.email_domain.value == ""){
    		document.getElementById("text_email").style.color = "red";
    		form.email_domain.focus();
    		return false;
    	}
    	if(form.address.value == ""){
    		document.getElementById("text_address").style.color = "red";
    		form.address.focus();
    		return false;
    	}

    	return true;
    }

    </script>
<title>내 정보 수정</title>
</head>
<body>
	<jsp:include page="../nav.jsp"/>
    <form id="container" action="/mealbox/user/updateUser" method="POST">
        <!--회원가입 헤더 부분-->
        <header>
            <h1 id="join_title" align="center">내 정보 수정</h1>
            <hr>
        </header>

        <!--회원가입 메인 부분-->
         <main id="join_main">
            <div id="join_label">
                <div><label for="id">아이디 <font color="red">*</font></label></div>
                <div><label for="password">비밀번호 <font color="red">*</font></label></div>
                <div><label for="check_password">비밀번호 확인 <font color="red">*</font></label></div>
                <div><label for="name">이름</label></div>
                <div><label for="phoneNum">전화번호</label></div>
                <div><label for="email">이메일</label></div>
                <div><label for="address">주소</label></div>
            </div>
            <div id="join_input">
                <input type="text" id="id" name="id" value="${user.id}" readonly>
                <div class="text" id="text_id">이미 존재하는 아이디입니다.</div>
                <input type="password" id="password" name="password" value="${user.password}">
                <div class="text" id="text_password">특수문자, 소문자, 숫자를 포함하며 8자 이상이어야 합니다.</div>
                <input type="password" id="check_password" name="check_password">
                <div class="text" id="text_checkPassword">비밀번호가 일치하지 않습니다.</div>
                <input type="text" id="name" name="name" value="${user.name}">
                <div class="text" id="text_name">이름은 필수입력 항목입니다.</div>
                <div id="phone">
                    <input type="text" id="phone_part1" name="phone_part1" maxlength="3" value="${phoneParts[0]}">
                    <span>-</span>
                    <input type="text" id="phone_part2" name="phone_part2" maxlength="4" value="${phoneParts[1]}">
                    <span>-</span>
                    <input type="text" id="phone_part3" name="phone_part3" maxlength="4" value="${phoneParts[2]}">
                </div>
                <div class="text" id="text_phone">전화번호는 필수입력 항목입니다.</div>
                <div id="email">
                    <input type="text" id="email_id" name="email_id" value="${emailParts[0]}">
                    <span>@</span>
                    <input type="text" id="email_domain" name="email_domain" value="${emailParts[1]}">
                </div>
                <div class="text" id="text_email">이메일은 필수입력 항목입니다.</div>
                <input type="text" id="address" name="address" value="${user.address}">
                <div class="text" id="text_address">주소는 필수입력 항목입니다.</div>
            </div>
        </main>
        <div id="buttons">
            <button type="button" class="style_button" id="before" onclick="history.back();">취소</button>
            <input type="submit" class="style_button" id="after" value="저장" onclick="return checkValidForm();">
        </div>
    </form>
</body>
</html>