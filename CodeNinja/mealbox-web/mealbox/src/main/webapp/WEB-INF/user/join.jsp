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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/join.css"/>
    <link rel="shortcut icon" href="/mealbox/favicon.ico">
    <!--join.js-->
    <script type="text/javascript">
    var isIdChecked = false;
    
    function checkValidId(){
    	var id = document.getElementById("id").value;
    	var pw = document.getElementById("password").value;
    	var check_pw = document.getElementById("check_password").value;
    	var name = document.getElementById("name").value;
    	var phone_part1 = document.getElementById("phone_part1").value;
    	var phone_part2 = document.getElementById("phone_part2").value;
    	var phone_part3 = document.getElementById("phone_part3").value;
    	var email_id = document.getElementById("email_id").value;
    	var email_domain = document.getElementById("email_domain").value;
    	var address = document.getElementById("address").value;
    	
    	if(id == ""){
    		var textIdElement = document.getElementById("text_id");
    		textIdElement.style.color = "red";
            textIdElement.innerHTML = "아이디를 입력해주세요.";
            return;
    	}
    	var form = document.createElement("form");
    	form.method="GET";
    	form.action ="/mealbox/user/createUser";
    	
		var inputId = document.createElement("input");
		inputId.type="hidden";
		inputId.name="inputId";
		inputId.value=id;
		form.appendChild(inputId);
		
		var inputPw = document.createElement("input");
		inputPw.type="hidden";
		inputPw.name="inputPw";
		inputPw.value=pw;
		form.appendChild(inputPw);
		
		var inputCheckPw = document.createElement("input");
		inputCheckPw.type="hidden";
		inputCheckPw.name="inputCheckPw";
		inputCheckPw.value=pw;
		form.appendChild(inputCheckPw);
		
		var inputName = document.createElement("input");
		inputName.type="hidden";
		inputName.name="inputName";
		inputName.value=name;
		form.appendChild(inputName);
		
		var inputPhone_part1 = document.createElement("input");
		inputPhone_part1.type="hidden";
		inputPhone_part1.name="inputPhone_part1";
		inputPhone_part1.value=phone_part1;
		form.appendChild(inputPhone_part1);
		
		var inputPhone_part2 = document.createElement("input");
		inputPhone_part2.type="hidden";
		inputPhone_part2.name="inputPhone_part2";
		inputPhone_part2.value=phone_part2;
		form.appendChild(inputPhone_part2);
		
		var inputPhone_part3 = document.createElement("input");
		inputPhone_part3.type="hidden";
		inputPhone_part3.name="inputPhone_part3";
		inputPhone_part3.value=phone_part3;
		form.appendChild(inputPhone_part3);
		
		var inputEmail_id = document.createElement("input");
		inputEmail_id.type="hidden";
		inputEmail_id.name="inputEmail_id";
		inputEmail_id.value=email_id;
		form.appendChild(inputEmail_id);
		
		var inputEmail_domain = document.createElement("input");
		inputEmail_domain.type="hidden";
		inputEmail_domain.name="inputEmail_domain";
		inputEmail_domain.value=email_domain;
		form.appendChild(inputEmail_domain);
		
		var inputAddress = document.createElement("input");
		inputAddress.type="hidden";
		inputAddress.name="inputAddress";
		inputAddress.value=address;
		form.appendChild(inputAddress);
		
		document.body.appendChild(form);
		form.submit();
    }

    function checkValidForm(){
    	var form = document.getElementById("container");
    	if(form.id.value == ""){
    		var textIdElement = document.getElementById("text_id");
    		textIdElement.style.color = "red";
            textIdElement.innerHTML = "아이디를 입력해주세요";
    		form.id.focus();
    		return false;
    	}
    	
    	if(form.password.value == ""){
    		document.getElementById("text_password").style.color = "red";
    		form.password.focus();
    		return false;
    	} else {
    		document.getElementById("text_password").style.color = "white";
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
    	if(isIdChecked == false){
    		var textIdElement = document.getElementById("text_id");
            textIdElement.style.color = "red";
            textIdElement.innerHTML = "아이디 중복확인을 해주세요";
            form.id.focus();
            return false;
    	}
    	return true;
    }
    </script>
<title>회원가입</title>
</head>
<body>
	<jsp:include page="../nav.jsp"/>
    <form name="form" id="container" action="/mealbox/user/createUser" method="POST">
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
                <input type="text" id="id" name="id" value="<%= request.getAttribute("inputId") != null ? request.getAttribute("inputId") : "" %>">
                <div class="text" id="text_id">이미 존재하는 아이디입니다.</div>
                <input type="password" id="password" name="password" value="<%= request.getAttribute("inputPassword") != null ? request.getAttribute("inputPassword") : "" %>">
                <div class="text" id="text_password">특수문자, 소문자, 숫자를 포함하며 8자 이상이어야 합니다.</div>
                <input type="password" id="check_password" name="check_password" value="<%= request.getAttribute("inputCheckPw") != null ? request.getAttribute("inputCheckPw") : "" %>">
                <div class="text" id="text_checkPassword">비밀번호가 일치하지 않습니다.</div>
                <input type="text" id="name" name="name" value="<%= request.getAttribute("inputName") != null ? request.getAttribute("inputName") : "" %>">
                <div class="text" id="text_name">이름은 필수입력 항목입니다.</div>
                <div id="phone">
                    <input type="text" id="phone_part1" name="phone_part1" maxlength="3" value="<%= request.getAttribute("inputPhone_part1") != null ? request.getAttribute("inputPhone_part1") : "" %>">
                    <span>-</span>
                    <input type="text" id="phone_part2" name="phone_part2" maxlength="4" value="<%= request.getAttribute("inputPhone_part2") != null ? request.getAttribute("inputPhone_part2") : "" %>">
                    <span>-</span>
                    <input type="text" id="phone_part3" name="phone_part3" maxlength="4" value="<%= request.getAttribute("inputPhone_part3") != null ? request.getAttribute("inputPhone_part3") : "" %>">
                </div>
                <div class="text" id="text_phone">전화번호는 필수입력 항목입니다.</div>
                <div id="email">
                    <input type="text" id="email_id" name="email_id" value="<%= request.getAttribute("inputEmail_id") != null ? request.getAttribute("inputEmail_id") : "" %>">
                    <span>@</span>
                    <input type="text" id="email_domain" name="email_domain" value="<%= request.getAttribute("inputEmail_domain") != null ? request.getAttribute("inputEmail_domain") : "" %>">
                </div>
                <div class="text" id="text_email">이메일은 필수입력 항목입니다.</div>
                <input type="text" id="address" name="address" value="<%= request.getAttribute("inputAddress") != null ? request.getAttribute("inputAddress") : "" %>">
                <div class="text" id="text_address">주소는 필수입력 항목입니다.</div>
            </div>
            <div id="join_checkID"><button type="button" class="style_button" id="check_ID" onclick="checkValidId()">중복확인</button></div>
        </main>
        <div id="buttons">
            <button type="button" class="style_button" id="before" onclick="history.back();">이전</button>
            <input type="submit" class="style_button" id="after" value="다음" onclick="return checkValidForm();">
        </div>
        
       <% if(request.getAttribute("idExist") != null) { 
            Boolean idExist = (Boolean) request.getAttribute("idExist"); %>
            <script>
                var textIdElement = document.getElementById("text_id");
                if (<%= idExist %>) {
                    textIdElement.style.color = "red";
                    textIdElement.innerHTML = "이미 존재하는 아이디입니다.";
                } else {
                    textIdElement.style.color = "green";
                    textIdElement.innerHTML = "사용가능한 아이디입니다.";
                    isIdChecked = true;
                }
            </script>
        <% } %>
    </form>
</body>
</html>