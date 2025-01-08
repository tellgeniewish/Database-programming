<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>리뷰 등록/수정</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/review/reviewForm.css" />
        <link rel="shortcut icon" href="/mealbox/favicon.ico">
    </head>
    <body>    
        <jsp:include page="../nav.jsp"/>
        <form id="container" action="/mealbox/review/create" method="POST" enctype="multipart/form-data">
        <input type="hidden" id="productId" name="productId" value="${product.id}" />
        <input type="hidden" id="orderId" name="orderId" value="${orderId}"/>	     
        <input type="hidden" id="lineNo" name="lineNo" value="${lineNo}"/>
	        <div id="title" align="center" style="font-size: 36px; margin-top: 50px; margin-bottom: 10px;">리뷰 등록/수정</div>
		    <div id="line">
		        <hr style="width:1295px;"/>
		    </div>
	        <div id="reviewWrapper">
		        
	            <div id="formWrapper">                
	                <div id="itemList">
	                    <!-- 상품 내역 정보 -->	                    
                        <div class="item">
                            <div id="buyProduct">
                                <img alt="상품사진" src="<c:url value='/upload/${product.thumb}'/>" style="min-height: 89px; margin-top: 7px;"/>
                            </div>
                            <div id="productDescript">
	                            
                            		<div><strong>상품명: </strong>${product.name}</div>
                            		<br/>
	                    			<div><strong>상품설명: </strong>${product.description}</div>
	                            
	                        </div>
                    	</div>
	                </div>
	                <!-- 실제로 넘길 부분 -->
	               	<p align="left" style="margin-top: 20px; margin-left: 25px;"><strong>리뷰 쓰기</strong></p>
	               	<input id="writeReview" name="reviewText" placeholder="구매하신 상품에 대해 리뷰를 남겨주세요." value="${foundReview.reviewText}">
					<!-- input style="display: none;"name="productId" value="${product.id}" /> -->
	                <div id="show">
		                <div id="rate">
		                	<p align="left" style="margin-top: 10px; margin-bottom: 10px; margin-left: 30px;"><strong>평점</strong></p> 
	                        <select id="writeRating" name="rating" style="margin-left: 10px; text-align: center;">
						    <option value="0.0" <c:if test="${foundReview == null}">selected</c:if>>선택해주세요.</option>
						    <option value="0.5" <c:if test="${foundReview != null && foundReview.rating == 0.5}">selected</c:if>>0.5</option>
						    <option value="1.0" <c:if test="${foundReview != null && foundReview.rating == 1.0}">selected</c:if>>1.0</option>
						    <option value="1.5" <c:if test="${foundReview != null && foundReview.rating == 1.5}">selected</c:if>>1.5</option>
						    <option value="2.0" <c:if test="${foundReview != null && foundReview.rating == 2.0}">selected</c:if>>2.0</option>
						    <option value="2.5" <c:if test="${foundReview != null && foundReview.rating == 2.5}">selected</c:if>>2.5</option>
						    <option value="3.0" <c:if test="${foundReview != null && foundReview.rating == 3.0}">selected</c:if>>3.0</option>
						    <option value="3.5" <c:if test="${foundReview != null && foundReview.rating == 3.5}">selected</c:if>>3.5</option>
						    <option value="4.0" <c:if test="${foundReview != null && foundReview.rating == 4.0}">selected</c:if>>4.0</option>
						    <option value="4.5" <c:if test="${foundReview != null && foundReview.rating == 4.5}">selected</c:if>>4.5</option>
						    <option value="5.0" <c:if test="${foundReview != null && foundReview.rating == 5.0}">selected</c:if>>5.0</option>
						</select>
		                </div>
		                <!-- 사진 부분-->
	                    <div id="pics">
	                        <p align="left" style="margin-top: 10px; margin-bottom: 10px; margin-left: 30px;"><strong>사진을 첨부해주세요.</strong></p>
	                        <span id="pic">
	                        	<!--
	                        	<button type="button" id="pic1" name="reviewImg">+</button>
	                        	<button type="button" id="pic2" name="reviewImg2">+</button>
	                            <button type="button" id="pic3" name="reviewImg3">+</button>
	                            -->
	                            <input type="file" id="reviewImg" name="reviewImg" accept="image/*" style="display: none;" /> 
	                            <label for="reviewImg" id="pic1">+</label>
	                            
	                        </span>
	                    </div>
	                     	
	                </div>
	                <!-- 버튼 부분 -->	
	                <div id="btn">
	                    <button type="button" onclick="history.back();">
	                    	<strong>취소</strong>
	                    </button>
	                    <!-- 삭제 버튼을 조건부로 표시 --> 
	                          
                        <c:if test="${foundReview != null}">	
                        	<input style="display: none;" name="reviewId" value="${foundReview.reviewId}" />                    
                            <button type="button" onclick="deleteReview(${foundReview.reviewId})">
                                <strong>삭제</strong>
                            </button>
                        </c:if>
	                    <button type="submit">
							<strong>저장</strong>
						</button>	                    
	                </div>
	            </div>
	        </div>
        </form>
        <script>
        window.deleteReview = function deleteReview(reviewId) {
            if (confirm("정말로 이 리뷰를 삭제하시겠습니까?")) { 
                // AJAX 요청으로 DELETE 요청을 서버에 보냄
                //System.out.println("\n\n .jsp 함수 리뷰 아디"+reviewId);
                console.error("\n\n .jsp 함수 리뷰 아디"+reviewId);
                fetch(`/mealbox/review/delete?reviewId=${reviewId}`, {
                    method: 'DELETE',
                })
                .then(response => {
                    if (response.ok) {
                        alert("리뷰가 삭제되었습니다.");
                        window.location.href = '/mealbox/purchase/purchaseList/orderId?orderId=${orderId}';
                    } else {
                        alert("리뷰 삭제에 실패했습니다.");
                    }
                })
                .catch(error => {
                    alert("리뷰 삭제 중 오류가 발생했습니다.");
                    console.error(error);
                });
            }
        }

        </script>
    </body>
</html>