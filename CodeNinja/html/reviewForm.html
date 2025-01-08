<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Cart Page</title>
    <link rel="stylesheet" href="../css/cart.css" />
  </head>
  <body>
    <div id="nav">nav</div>
    <div id="cart">
      <h2 align="center">리뷰 작성</h2>
      <div id="itemList" align="center">
      <span>
      	<div>
	      	<img src="../images/cartItem.png" alt="상품 이미지" />
	    </div>
	    <div>
	        <p>상품명</p>
	        <p>상품설명</p>
        </div>
      </span>
        <hr />

        <!-- 상품 리스트 출력 -->
        <p>리뷰쓰기</p>
        <c:forEach var="product" items="${products}">
          <div class="item">
            <div class="itemInfo1">
              <img src="../images/cartItem.png" alt="상품 이미지" />
            </div>
            <div class="itemInfo2">
              <p>${product.name}</p>
              <form action="/mealbox/purchase/cart" method="post">
                 <!-- 상품명 및 현재 수량 전달 -->
                 <input type="hidden" name="productName" value="${product.name}">
                 <input type="hidden" name="quantity" value="${product.quantity}">
                 <!-- 수량 변경 버튼들 -->
                 <span>상품수량 </span> 
                 <span class="btnContainer">
	                 <button class="btnQ" type="submit" name="action" value="decrease" ${product.quantity == 1 ? 'disabled' : ''}>-</button>
	                 <span class="btnSpan">${product.quantity}</span>
	                 <button class="btnQ" type="submit" name="action" value="increase">+</button>
                 </span>
              </form>
            </div>
            <div class="itemInfo3" align="right">
              <!-- 삭제 버튼: 해당 상품 ID를 서버에 전달하여 삭제 -->
              <form action="/mealbox/purchase/cart" method="post" style="display:inline;">
                <input type="hidden" name="deleteItemId" value="${product.name}">
                <button type="submit" class="close-btn">X</button>
              </form>
              <p>총가격 ${product.totalPrice}원</p>
            </div>
          </div>
        </c:forEach>
      </div>
      <div id="totalMoney">
        <p>결제예정금액</p>
        <p id="money">${totalPrice}원</p>
      </div>
       <form id="btn" action="review/item" method="post">
          <button type="submit">취소</button>
          <button type="submit">저장</button>
        </form>
    </div>
  </body>
</html>