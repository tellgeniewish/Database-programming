<%--
기능: 메인 페이지 jsp
작성자: 신윤지
마지막 수정일: 2024-11-19
 --%>
 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <!DOCTYPE html>
 <html lang="ko-kr">
 	<head>
 		<meta charset="UTF-8" />
 		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="preconnect" href="https://rsms.me/" />
		<link rel="stylesheet" href="https://rsms.me/inter/inter.css" />
 		<link rel="stylesheet" type="text/css" href="css/main.css" />
	<link rel="shortcut icon" href="/mealbox/favicon.ico">
 		<script>
        function changeOrder(orderBy) {
            var currentUrl = new URL(window.location.href);
            currentUrl.searchParams.set('orderBy', orderBy);
            window.location.href = currentUrl.toString();
        }
 		</script>
 		<title>Mealbox</title>
 	</head>
 	<body>
 	<%@ include file="../nav.jsp" %>
    <div id="mainContainer">
    <!-- 상품 정렬 방식 -->
        <ul id="orderContainer">
            <li onclick="changeOrder('newest')" class="${currentOrder == 'newest' ? 'active' : ''}">최신등록순</li>
            <li onclick="changeOrder('lowPrice')" class="${currentOrder == 'lowPrice' ? 'active' : ''}">낮은가격순</li>
            <li onclick="changeOrder('highPrice')" class="${currentOrder == 'highPrice' ? 'active' : ''}">높은가격순</li>
            <li onclick="changeOrder('highRating')" class="${currentOrder == 'highRating' ? 'active' : ''}">평점높은순</li>
        </ul>

        <!-- 상품 리스트 -->
        <div id="cardContainer">
            <c:forEach var="product" items="${productList}">
                <a href="<c:url value='/product/detail'><c:param name='id' value='${product.id}'/></c:url>">
                    <div id="card">
						<img id="cardImg" src="<c:url value='/upload/${product.thumb}'/>" />
                        <div id="cardText">
                            <div id="cardName">${product.name}</div>
                            <div id="cardPrice">${product.price}원</div>
                            <div id="cardDesc">${product.description}</div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
</body>
</html>