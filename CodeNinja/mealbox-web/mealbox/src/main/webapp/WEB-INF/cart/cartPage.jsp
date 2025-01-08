<%--
기능: cartPage jsp
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
    <title>장바구니</title>
	<link rel="stylesheet" href="../css/cartPage.css" />
	<link rel="shortcut icon" href="/mealbox/favicon.ico">
  </head>
  <body>
	<jsp:include page="../nav.jsp"/>
    <div id="container">
    	<div id="title">장바구니 </div>
    	<div id="line">
    		<hr>	
    	</div>
    	<div id="listContainer">
    		<p>상품내역</p>
    		<hr>
    		<c:forEach var="detail" items="${combinedProductDetails}">
			    <div class="item">
			    	<div class="itemInfo1">
			    		<img alt="상품사진" src="<c:url value='/upload/${detail.productDetail.thumb}'/>">
			    	</div>
			    	<div class="itemInfo2">
			    		<div class="itemInfo2-name">
				        	<p><strong>상품명:</strong> ${detail.productDetail.name}</p>
				        </div>
				        <div class="itemInfo2-quantity">
				        	<p><strong>수량:</strong> </p>
							<form action="${pageContext.request.contextPath}/cart/update" method="GET">
		               			<input type="hidden" name="userId" value="${detail.cartProduct.userId}" />
				               	<input type="hidden" name="productId" value="${detail.cartProduct.productId}" />
				               	<input type="hidden" name="quantity" value="${detail.cartProduct.quantity}" />
				               	<input type="hidden" name="cartItemPrice" value="${detail.cartProduct.cartItemPrice}" />
				               	<input type="hidden" name="updateValue" value="decrease" />
				               	<input type="hidden" name="page" value="cartPage" />
             					<button class="btnQ" type="submit" name="action" value="decrease">-</button>
		                    </form>
					        <p>${detail.cartProduct.quantity}</p>
		               		<form action="${pageContext.request.contextPath}/cart/update" method="GET">
		               			<input type="hidden" name="userId" value="${detail.cartProduct.userId}" />
				               	<input type="hidden" name="productId" value="${detail.cartProduct.productId}" />
				               	<input type="hidden" name="quantity" value="${detail.cartProduct.quantity}" />
				               	<input type="hidden" name="cartItemPrice" value="${detail.cartProduct.cartItemPrice}" />
				               	<input type="hidden" name="updateValue" value="increase" />
				               	<input type="hidden" name="page" value="cartPage" />
             					<button class="btnQ" type="submit" name="action" value="increase">+</button>
		                    </form>
	                    </div>
			        </div>
			        <div class="itemInfo3">
	               		<form action="${pageContext.request.contextPath}/cart/remove" method="GET">
	               			<input type="hidden" name="userId" value="${detail.cartProduct.userId}" />
			               	<input type="hidden" name="productId" value="${detail.cartProduct.productId}" />
			               	<input type="hidden" name="page" value="cartPage" />
		                	<button type="submit" class="close-btn">X</button>
	                    </form>
				        <p><strong>총가격 </strong> ${detail.cartProduct.cartItemPrice}원</p>
			        </div>
			    </div>
			</c:forEach>
			
		    <c:if test="${empty combinedProductDetails}">
		        <p>장바구니에 담긴 상품이 없습니다.</p>
		    </c:if>
    	</div>
    	<div id="totalMoney">
	        <p>결제예정금액</p>
	        <p id="money">${totalPrice}원</p>
        </div>
        <div id="btn">
        	<c:choose>
			    <c:when test="${empty combinedProductDetails}">
			        <button type="button" onClick="alert('상품을 담은 후 구매를 진행해주세요.');">구매하기</button>
			    </c:when>
			    <c:otherwise>
			        <form action="${pageContext.request.contextPath}/cart/view" method="POST">
			            <input type="hidden" name="page" value="purchasePage" />
			            <button type="submit">구매하기</button>
			        </form>
			    </c:otherwise>
			</c:choose>
	    </div>
    </div>
  </body>
</html>