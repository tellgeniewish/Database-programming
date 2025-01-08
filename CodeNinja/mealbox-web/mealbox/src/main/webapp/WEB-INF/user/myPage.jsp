<%--
기능: myPage jsp
작성자: 장고은
마지막 수정일: 2024-11-04
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>myPage</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/myPage.css" />
	<link rel="shortcut icon" href="/mealbox/favicon.ico">
  </head>
  <body>
    <jsp:include page="../nav.jsp"/>
    <div id="container">
    	<div id="title">마이페이지</div>
    	<div id="line">
    		<hr>	
    	</div>
    	<div id="btnContainer">
			<form  action="${pageContext.request.contextPath}/cart/view" method="POST">
			    <input type="hidden" name="page" value="cartPage" />
				<button type="submit">장바구니 바로가기</button>
			</form>
			<form action="${pageContext.request.contextPath}/purchase/purchaseList" method="POST">
				<button type="submit">주문목록 바로가기</button>
			</form>
			<form action="/mealbox/user/updateUser" method="GET"><!-- post로 하면 안됨. updateUserController에서 get인지 post인지 따라 다르게 코드를 작성함 -->
				<button type="submit">내 정보 수정하기</button>
			</form>
			<form action="/mealbox/user/deleteUser" method="POST">
				<button type="submit">탈퇴하기</button>
			</form>
    	</div>
    </div>
  </body>
</html>