 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문 상세 정보</title>
    <link rel="stylesheet" href="../../css/orderProductDetail.css" />
	<link rel="shortcut icon" href="/mealbox/favicon.ico">
</head>
<body>
	<jsp:include page="../nav.jsp" />
    <div id="container">
      <div id="title">주문 상세 정보</div>
      <div id="line">
        <hr />
      </div>
      <div id="listContainer">
        <p>상품 내역</p>
        <hr />
        <c:forEach var="detail" items="${combinedProductDetails}">
		    <div class="item">
		    	<div class="itemInfo1">
		    		<img alt="상품사진" src="<c:url value='/upload/${detail.productDetail.thumb}'/>" >
		    	</div>
		    	<div class="itemInfo2">
			        <p><strong>상품명:</strong> ${detail.productDetail.name}</p>
			        <p><strong>주문 번호:</strong> ${detail.orderProduct.lineNo}</p>
			        <p><strong>수량:</strong> ${detail.orderProduct.quantity}</p>
			        <p><strong>가격:</strong> ${detail.orderProduct.orderItemPrice}원</p>
		        </div>
		        <div class="itemInfo3">
		        	
               	<form action="${pageContext.request.contextPath}/review/form" method="POST">
               		<input type="hidden" name="orderId" value="${detail.orderProduct.orderId}" />
               		<input type="hidden" name="lineNo" value="${detail.orderProduct.lineNo}" />
               		<input type="hidden" name="productId" value="${detail.productDetail.id}" />
                    <button class="btn" type="submit">리뷰 등록/수정</button>
                </form>
                     
                <form action="${pageContext.request.contextPath}/product/detail" method="POST">
      			   <input type="hidden" name="id" value="${detail.productDetail.id}" /> 
                   <button class="btn" type="submit">상품 상세 정보</button>
                </form>
		    	</div>
		    </div>
		</c:forEach>
	    <c:if test="${empty combinedProductDetails}">
	        <p>해당 상품 정보를 찾을 수 없습니다.</p>
	    </c:if>
	   	<form action="${pageContext.request.contextPath}/purchase/purchaseList" method="POST">
				<button  id="backBtn" type="submit">주문목록 바로가기</button>
		</form>
      </div>
    </div>
</body>
</html>