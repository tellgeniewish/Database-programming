<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>리뷰 페이지</title>
        <link rel="stylesheet" href="/mealbox/css/review/detailProductReviewPage.css">
    </head>
    <body>
        <h2>상품 리뷰</h2>
        <div class="container">
            <div class="sort_options">
                <span class="sort_option" onclick="location.href='?sort=latest'">최신 순 |</span>
                <span class="sort_option" onclick="location.href='?sort=highRate'"> 평점 높은 순 |</span>
                <span class="sort_option" onclick="location.href='?sort=lowRate'">평점 낮은 순</span>
            </div>
            <div id="reviews">
                <c:forEach var="review" items="${reviews}">
                    <div class="review_header">
                        <div>${review.nickname}</div>
                    </div>
                    <!--  -->
					<span class="rating">
                        <c:set var="fullStars" value="${floor(review.rating)}" />
                        <c:set var="halfStar" value="${review.rating - fullStars >= 0.5}" />

                        <c:forEach var="i" begin="1" end="${fullStars}">
					        ⭐
					    </c:forEach>
					
					    <c:if test="${halfStar}">
					        ✨
					    </c:if>
                    </span>

                    <span>${review.date}</span>
                    <!-- 
					<div class="review_text">
                        <p>${review.text}</p>
                        <img src="" alt="상품 이미지" class="product_image">
                    </div>
                     -->
                   <!-- <div class="footer">
                         <p>구매상품: ${review.product}</p>
                    </div> -->
                    <hr>           
                </c:forEach>                    
            </div>
        </div>
    </body>
</html>