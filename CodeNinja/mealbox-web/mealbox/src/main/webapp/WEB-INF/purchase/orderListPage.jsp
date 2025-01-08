<%-- 기능: orderListPage jsp 작성자: 장고은 마지막 수정일: 2024-11-04 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>주문목록</title>
    <link rel="stylesheet" href="../css/orderListPage.css" />
	<link rel="shortcut icon" href="/mealbox/favicon.ico">
  </head>
  <body>
    <jsp:include page="../nav.jsp" />
    <div id="container">
      <div id="title">주문목록</div>
      <div id="line">
        <hr />
      </div>
      <div id="listContainer">
        <p>구매상품내역</p>
        <hr />
        <c:forEach var="order" items="${orderList}">
		    <div class="item">
		        <!-- 주문 정보 -->
		        <div class="orderInfo">
		            <p>주문 번호: ${order.orderId}</p>
		            <p>주문 날짜: ${order.orderAt}</p>
		            <p>총 금액: ${order.totalPrice}원</p> 
		        </div>
		        <div>
		            <!-- 주문 상품 목록 -->	
	               <form action="${pageContext.request.contextPath}/purchase/purchaseList/orderId" method="GET">
            			<input type="hidden" name="orderId" value="${order.orderId}" />
                        <button class="btn" type="submit">주문 상세 정보 보기</button>
                    </form>
			    </div>
		    </div>
		</c:forEach>
      </div>
    </div>
  </body>
</html>