<%--
기능: nav jsp
작성자: 신윤지
마지막 수정일: 2024-11-27
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://rsms.me/" />
<link rel="stylesheet" href="https://rsms.me/inter/inter.css" />
<link rel="stylesheet" type="text/css" href="/mealbox/css/nav.css" />
<script>
	<!-- 사이드 바 오픈 관련 코드 -->
	// 페이지 처음 로딩 시 
	window.onload = function() {
		// 인원별 아코디언 하단 부분 숨김 처리
		document.getElementById("peopleSelection").style.display = "none";

		// 햄버거 메뉴 클릭 시 사이드바 보이게끔 처리
		document.getElementById("menuOpen").addEventListener("click",
				function() {
					document.getElementById("sidebar").style.display = "block";
				});
		// 사이드바 닫히는 버튼 클릭 시 사이드바 숨김 처리
		document.getElementById("menuClose").addEventListener("click",
				function() {
					document.getElementById("sidebar").style.display = "none";
				});

		// 인원별 아코디언 클릭 시
		document.getElementById("peopleSelectionOpen").addEventListener(
						"click",
						function() {
							var open = document
									.getElementById("peopleSelectionOpen");
							var selection = document
									.getElementById("peopleSelection");

							if (selection.style.display == "none") {
								open.style.backgroundImage = `url(/mealbox/images/close.png)`;
								selection.style.display = "block";
							} else {
								open.style.backgroundImage = `url(/mealbox/images/open.png)`;
								selection.style.display = "none";
							}
						});
		

		function performSearch() {
			if(searchForm.keyword.value == "") {
				alert("검색어를 입력해주세요.");
				return;
			}
			
	        window.location.href = '/mealbox/product?categoryType=search&categoryValue=' + searchForm.keyword.value;
		}
		
		// 검색 버튼 클릭 시
		document.getElementById("search_icon").addEventListener("click", function() {
			performSearch();
		});
		
		// 엔터 입력 시
		document.getElementById("search_text").addEventListener("keypress", function(event) {
	        event.preventDefault();
		    if (event.keyCode === 13) {
		        performSearch();
		    }
		});
		
	};
</script>
</head>
<body>
	<!--검색, 장바구니, join, login-->
	<div id="navContainer">
		<!--메뉴아이콘-->
		<div id="leftSide">
			<input type="button" id="menuOpen" />
		
			<a href="${pageContext.request.contextPath}/product">
				<img src="/mealbox/images/codeNinja.png" />
			</a>
		</div>
		<div id="navInNav">
		<!--검색-->
		<div id="search">
			<form name="searchForm" method="POST" action="<c:url value='/product' />">
				<input type="text" id="search_text" name="keyword" placeholder="검색어" />
				<!--검색아이콘-->
				<img id="search_icon" src="/mealbox/images/search.png" align="center" />
			</form>
		</div>
	
		<c:set var="loginId" value="${sessionScope.userId}" />
	
		</span>
		<c:choose>
		    <c:when test="${empty loginId}">
				<div class="navButton" onclick="document.getElementById('joinForm').submit();">
					<form id="joinForm" action="/mealbox/user/join/form" method="GET" >
					</form>
				JOIN</div>
				<div class="navButton" onclick="document.getElementById('loginForm').submit();">
					<form id="loginForm" action="/mealbox/user/login/form" method="GET">
					</form>
				LOGIN</div>
			</c:when>
			
		    <c:when test="${loginId eq 'admin'}">
				<div class="navButton" onclick="document.getElementById('adminForm').submit();">
					<form id="adminForm" action="/mealbox/admin" method="GET">
					</form>
				상품관리</div>
				<div class="navButton" onclick="document.getElementById('listUserForm').submit();">
					<form id="listUserForm" action="/mealbox/user/listUser" method="GET">
					</form>
				회원관리</div>
				<div class="navButton" onclick="document.getElementById('logoutForm').submit();">
					<form id="logoutForm" action="/mealbox/user/logout" method="GET" >
					</form>
				LOGOUT</div>
			</c:when>
			
			<c:otherwise>
				<div id="basket">
					<form  action="${pageContext.request.contextPath}/cart/view" method="POST">
			    		<input type="hidden" name="page" value="cartPage" />
						<button type="submit" id="btn_basket">
							<img id="basket_icon" src="/mealbox/images/basket.png" align="center" />
						</button>
					</form>
				</div>
				<div class="navButton" onclick="document.getElementById('logoutForm').submit();">
					<form id="logoutForm" action="/mealbox/user/logout" method="GET" >
					</form>
				LOGOUT</div>
				<div class="navButton" onclick="document.getElementById('myPageForm').submit();">
					<form id="myPageForm" action="/mealbox/user/readUser" method="GET">
					</form>
				MYPAGE</div>
			</c:otherwise>
		</c:choose>	
		
		</div>
	</div>
	
	<!--사이드 바-->
	<aside id="sidebar">
		<section class="closeSection">
			<img id="menuClose" src="/mealbox/images/sidebar_close.png" />
		</section>
	    <section class="selection">
    	    <a href="<c:url value='/product'><c:param name='categoryType' value='food'/><c:param name='categoryValue' value='1'/></c:url>">
        	    한식
        	</a>
    	</section>
    	<section class="selection">
        	<a href="<c:url value='/product'><c:param name='categoryType' value='food'/><c:param name='categoryValue' value='2'/></c:url>">
            	양식
        	</a>
    	</section>
    	<section class="selection">
        	<a href="<c:url value='/product'><c:param name='categoryType' value='food'/><c:param name='categoryValue' value='3'/></c:url>">
            	중식
        	</a>
    	</section>
	    <section class="selection">
    	    <a href="<c:url value='/product'><c:param name='categoryType' value='food'/><c:param name='categoryValue' value='4'/></c:url>">
        	    분식
        	</a>
    	</section>
    	
		<section class="selection">
			인원별 <input type="button" id="peopleSelectionOpen"/>
		</section>
		<section id="peopleSelection">
			<div id="smallerSelection">
				<a href="<c:url value='/product'><c:param name='categoryType' value='person'/><c:param name='categoryValue' value='1'/></c:url>">
    	            1~2인분
        	    </a>
            </div>
			<div id="biggerSelection">
            	<a href="<c:url value='/product'><c:param name='categoryType' value='person'/><c:param name='categoryValue' value='2'/></c:url>">
                	3~4인분
            	</a>
            </div>
		</section>
	</aside>
</body>
</html>