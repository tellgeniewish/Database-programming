<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--폰트-->
<link rel="preconnect" href="https://rsms.me/" />
<link rel="stylesheet" href="https://rsms.me/inter/inter.css" />
<!--adminUserPage.css: css경로 변경하지 말 것-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminUserPage.css"/>
<link rel="shortcut icon" href="/mealbox/favicon.ico">
<title>회원관리페이지</title>
</head>
<body>
	<jsp:include page="../nav.jsp"/>
    <form id = "container" action="/mealbox/user/deleteUser" method="POST">
        <!--adminUserPage header부분-->
        <header>
            <div>회원관리</div>
            <button type="submit">선택삭제</button>
        </header>
        <main id = "adminUserPageMain">
            <table>
                <tr>
                    <th><input type="checkbox"/></th>
                    <th>회원번호</th>
                    <th>아이디</th>
                    <th>비밀번호</th>
                    <th>이름</th>
                    <th>전화번호</th>
                    <th>주소</th>
                    <th>이메일</th>
                    <th>주문건수</th>
                    <th>등록리뷰수</th> 
                </tr>
				<c:forEach var="user" items="${userList}" varStatus="status">
					<tr>
						<td><input type="checkbox" name="userId" value="${user.id}"/></td>
						<td>${status.index + 1}</td>
						<td>${user.id}</td>
						<td>${user.password}</td>
						<td>${user.name}</td>
						<td>${user.phone}</td>
						<td>${user.address}</td>
						<td>${user.email}</td>
						<td>${user.orderCount}</td>
						<td>${user.reviewCount}</td>
					</tr>
				</c:forEach>
            </table>
        </main>
    </form>
</body>
</html>