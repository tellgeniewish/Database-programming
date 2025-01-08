<%--
기능: purchasePage jsp
작성자: 장고은
마지막 수정일: 2024-11-16
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>구매하기</title>
	<link rel="stylesheet" href="../css/purchasePage.css" />
	<link rel="shortcut icon" href="/mealbox/favicon.ico">
    <script>
		let today = new Date();
		let tomorrow = new Date(today.setDate(today.getDate() + 1));
		let year = tomorrow.getFullYear();
		let month = tomorrow.getMonth() + 1;
		let day = tomorrow.getDate();
		if (month < 10) {
		  month = "0" + month;
		}
		if (day < 10) {
		  day = "0" + day;
		}
		let availableDay = year+"-"+month+"-"+day;
		
		window.addEventListener('DOMContentLoaded', function(){
			document.getElementById("deliveryDate").setAttribute("min", availableDay);
		});
		
		// 재고 >= 장바구니 수량일 때만 구매 가능하도록
		function validateCart(event) {
		  const items = document.querySelectorAll(".item"); // 상품 목록
		  let isValid = true;
		
		  items.forEach(item => {
		      const nameField = item.querySelector(".itemInfo2-name p span"); // 상품 이름
		      const quantityField = item.querySelector(".itemInfo2-quantity p span"); // 장바구니 수량
		      const availableQuantityField = item.querySelector(".availableQuantity"); // 재고 수량 필드
		
		      const name = nameField ? nameField.textContent.trim() : "알 수 없는 상품"; // 상품 이름 가져오기
		      const quantity = parseInt(quantityField.textContent.trim(), 10) || 0; // 장바구니 수량
		      const availableQuantity = parseInt(availableQuantityField.value, 10) || 0; // 재고 수량
		   	// 디버깅용 출력
		      console.log("상품명:", name);
		      console.log("수량:", quantity);
		      console.log("재고:", availableQuantity);
		      
		      if (quantity > availableQuantity) {
		        isValid = false;
		        alert('"' + name + '" 상품의 수량이 재고(' + availableQuantity + ')를 초과했습니다.');
		      }
		    });
		
		  if (!isValid) {
		    event.preventDefault(); // 버튼 클릭 무효화 (폼 제출 중단)
		  }
		}
	</script>
  </head>
  <body>
  	<% 
        // 서버에서 HttpSession에 값 저장
        session.setAttribute("redirectPage", "purchasePage");
    %>
	<jsp:include page="../nav.jsp"/>
    <div id="container">
    	<div id="title">구매하기</div>
    	<div id="line">
    		<hr>	
    	</div>
       	<form action="${pageContext.request.contextPath}/order/create" method="POST">
    		<div id="contentContainer">
	    		<div id="formContainer">
		            <div id="form1">
		                <h3>주문자</h3>
		                <hr>
		                <table>
		                    <tr>
		                        <td>이름</td>
		                        <td>
		                            <input type="text" name="purchaser" placeholder="이름을 입력해 주세요." required>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>휴대폰</td>
		                        <td>
		                            <input type="text" name="purPhone" placeholder="휴대폰 번호를 입력해 주세요." required>
		                        </td>
		                    </tr>
		                </table>
		            </div>
		            <div id="form2">
		                <h3>배송지 정보</h3>
		                <hr>
		                <table>
		                    <tr>
		                        <td>받는 분</td>
		                        <td>
		                            <input type="text" name="recipient" placeholder="이름을 입력해 주세요." required>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>휴대폰</td>
		                        <td>
		                            <input type="text" name="recPhone" placeholder="휴대폰 번호를 입력해 주세요." required>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>주소</td>
		                        <td>
		                            <input type="text" name="deliveryAddress" placeholder="주소를 입력해 주세요." required>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>배송희망일</td>
		                        <td>
		                            <input type="date" name="deliveryDate" id="deliveryDate" required>
		                        </td>
		                    </tr>
		                </table>
		            </div>
			    </div>
	    		<div id="cartContainer">
		        	<div id="listContainer">
		            	<p>주문상품</p>
		    			<hr>
		    			<c:forEach var="detail" items="${sessionScope.cartProducts}">
					    	<div class="item">
						    	<div class="itemInfo1">
						    		<img alt="상품사진" src="<c:url value='/upload/${detail.productDetail.thumb}'/>">
						    	</div>
						    	<div class="itemInfo2">
						    		<div class="itemInfo2-name">
							        	<p><strong>상품명:</strong> <span>${detail.productDetail.name}</span></p>
							        </div>
							        <div class="itemInfo2-quantity">
							        	<p><strong>수량:</strong> <span>${detail.cartProduct.quantity}</span></p>
				                    </div>
						        </div>
						        <div class="itemInfo3">
							        <p><strong>총가격 </strong> ${detail.cartProduct.cartItemPrice}원</p>
						        </div>
		    					<input type="hidden" class="availableQuantity" value="${detail.productDetail.stock}" />
					    	</div>
						</c:forEach>
					
					    <c:if test="${empty sessionScope.cartProducts}">
					        <p>장바구니에 담긴 상품이 없습니다.</p>
					    </c:if>
			    	</div>
			    	<div id="totalMoney">
				        <p>결제예정금액</p>
				        <p id="money">${sessionScope.cartTotalPrice}원</p>
			        </div>
			        <div id="btn">
	            		<input type="hidden" name="totalPrice" value="${sessionScope.cartTotalPrice}" />
		          		<button type="submit" onclick="validateCart(event)">구매하기</button>
				    </div>
				</div>
    		</div>
		</form>
    </div>

  </body>
</html>