<%--
기능: 상품 디테일 페이지 jsp
작성자: 신윤지
마지막 수정일: 2024-11-19
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
 	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="preconnect" href="https://rsms.me/" />
	<link rel="stylesheet" href="https://rsms.me/inter/inter.css" />
	<link rel="stylesheet" type="text/css" href="/mealbox/css/detailPage.css">
	<link rel="stylesheet" href="/mealbox/css/review/detailProductReviewPage.css">
	<link rel="shortcut icon" href="/mealbox/favicon.ico">
	<script>
		function increaseQuantity() {
			var price = parseInt("${product.price}");
			
			document.getElementById("quantity").value = Number(document.getElementById("quantity").value)+1;
			document.getElementById("how_much").innerHTML = price * Number(document.getElementById("quantity").value) + "원";
			document.getElementById("total_price").innerHTML = price * Number(document.getElementById("quantity").value) + "원";
		}
		
		function decreaseQuantity() {
			var price = parseInt("${product.price}");
			
			if(document.getElementById("quantity").value==="1") {
				alert("1 이하의 수량은 주문하실 수 없습니다.");
				return;
			}
			document.getElementById("quantity").value = Number(document.getElementById("quantity").value)-1;
			document.getElementById("how_much").innerHTML = price * Number(document.getElementById("quantity").value) + "원";
			document.getElementById("total_price").innerHTML = price * Number(document.getElementById("quantity").value) + "원";
		}
		function checkLoginAndSubmit(event, form) {
	        // 서버에서 전달된 sessionScope.userId 값을 확인
	        var loginId = "${sessionScope.userId}";

	        if (!loginId) {
	            alert('로그인 후 이용해주세요.');
	            event.preventDefault(); // 폼 제출 중단
	            return false;
	        }

	        // 사용자가 선택한 수량 설정
	        const quantityInput = form.querySelector("#hiddenQuantity");
	        const quantityField = document.getElementById("quantity");
	        if (quantityField && quantityInput) {
	            quantityInput.value = quantityField.value;
	        }
	        

	     // 가격 설정
	        const cartItemPriceInput = form.querySelector("#hiddenCartItemPrice");
	        const price = Number(document.getElementById("how_much").textContent.replace("원", ""));

	        if (cartItemPriceInput) {
	            cartItemPriceInput.value = price;
	        }

	        // 폼 제출
	        return true;
	    }
	</script>
	<title>상품 상세 페이지</title>
</head>
<body>
 	<%@ include file="../nav.jsp" %>
	<div class="container_meal">
		<div class="meal_img">
			<img id="product_image" src="<c:url value='/upload/${product.thumb}'/>"
				alt="<c:out value='${product.name}'/>">
		</div>
		<div class="details_container">
			<h3 id="product_name">
				<c:out value="${product.name}" />
			</h3>
			<div class="rating">
				<span> 
					<c:forEach var="rate" begin="1" end="${avrRev}">
						★
					</c:forEach> 
					<c:forEach var="rate" begin="${avrRev + 1}" end="5">
						 ☆
					</c:forEach>
				</span> <span id="product_rating_score">&nbsp;｜&nbsp;
					<fmt:formatNumber var="fmAvg" value="${avrRev}" pattern="#.#" />
					${fmAvg}
				</span>
			</div>
			<p class="price" id="product_price">${product.price}원</p>
			<div class="quantity">
				<div id="product_description">
					<c:out value="${product.description}" />
				</div>
				<hr>
				<div class="check_quantity">
					<div class="plusOrMinus">
						<button class="decrease" onClick="decreaseQuantity()">-</button>
						<input type="number" id="quantity" value="1" min="1" readonly>
						<button class="increase" onClick="increaseQuantity()">+</button>
					</div>
					<span id="how_much">${product.price}원</span>
				</div>
			</div>
			<p class="total_price">
				<sup class="maybe_price">구매 예정 금액</sup>
				<span id="total_price">${product.price}원</span>
			</p>
			<div class="buy_yet">
				<c:set var="loginId" value="${sessionScope.userId}" />
				<form id="cartForm" action="${pageContext.request.contextPath}/cart/add" method="GET" onsubmit="return checkLoginAndSubmit(event, this)">
				    <input type="hidden" name="productId" value="${product.id}" />
				    <input type="hidden" name="quantity" id="hiddenQuantity" value="" />
				    <input type="hidden" name="cartItemPrice" id="hiddenCartItemPrice" value="" />
				  	<% 
				        // 서버에서 HttpSession에 값 저장
				        session.setAttribute("redirectPage", "cartPage");
				    %>
				    <input type="hidden" name="btnName" value="장바구니" />
				    <button class="add_cart" type="submit">장바구니</button>
				</form>
				<form id="purchaseForm" action="${pageContext.request.contextPath}/cart/add" method="GET" onsubmit="return checkLoginAndSubmit(event, this)">
				    <input type="hidden" name="productId" value="${product.id}" />
				    <input type="hidden" name="quantity" id="hiddenQuantity" value="" />
				    <input type="hidden" name="cartItemPrice" id="hiddenCartItemPrice" value="" />
				  	<% 
				        // 서버에서 HttpSession에 값 저장
				        session.setAttribute("redirectPage", "purchase");
				    %>
			        <button class="buy_now" type="submit">바로구매</button>
			    </form>
			</div>
		</div>
	</div>
	
    <div class="reviewContainer">
        <hr style="width:1295px;" />
		<h2>상품 리뷰</h2>
            <div class="sort_options">
                <span class="sort_option" onclick="location.href='?id=${product.id}&sort=latest'">최신 순 |</span>
                <span class="sort_option" onclick="location.href='?id=${product.id}&sort=highRate'"> 평점 높은 순 |</span>
                <span class="sort_option" onclick="location.href='?id=${product.id}&sort=lowRate'">평점 낮은 순</span>
            </div>
            <div id="reviews">
                <c:forEach var="review" items="${reviews}">
                    <div class="review_header">
                        <div>${review.nickname}</div>
                    </div>
                    <div id="starAndDate">
						<span class="rating">
							<fmt:formatNumber var="fullStars" value="${review.rating}" type="number" pattern="#" />
	                        <c:set var="halfStar" value="${review.rating - fullStars >= 0.5}" />
	
	                        <c:forEach var="i" begin="1" end="${fullStars}">
						        ⭐
						    </c:forEach>
						
						    <c:if test="${halfStar}">
						        ✨
						    </c:if>
	                    </span>
	
	                    <span>${review.date}</span>
                    </div>
                    <c:if test="${not empty review.reviewImg}">
					    <img alt="리뷰사진" src="<c:url value='/upload/${review.reviewImg}'/>" style="height: 70px; width: 150px;"/>
					</c:if>                    
                    <div id="reviewTextWrapper">${review.reviewText}</div>
                    <hr style="width:1295px;">           
                </c:forEach>                    
            </div>
        </div>
</body>
</html>
