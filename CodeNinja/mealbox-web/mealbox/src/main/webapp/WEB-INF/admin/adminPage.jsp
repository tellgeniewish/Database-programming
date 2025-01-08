<%--
기능: adminPage jsp 
작성자: 신윤지
마지막 수정일: 2024-11-27
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="preconnect" href="https://rsms.me/" />
<link rel="stylesheet" href="https://rsms.me/inter/inter.css" />
<link rel="stylesheet" type="text/css" href="css/admin.css" />
	<link rel="shortcut icon" href="/mealbox/favicon.ico">
<script>
	function submitForm(action) {
    	const form = document.getElementById('productForm');
    	form.action = '${pageContext.request.contextPath}/admin/' + action;
    
    	if(action === 'create') {
    		if(!validateCreation()) return;
    	}
    	
    	if(action === 'update' || action === 'delete'){
    		if(!validateSelected()) return;
    	}
	    
	    form.submit();
	}
	
	function validateCreation() {
	    const name = document.querySelector('input[name="newName"]').value;
	    const price = document.querySelector('input[name="newPrice"]').value;
	    const stock = document.querySelector('input[name="newStock"]').value;
		
		if(!name) {
			alert("상품명 입력 후 상품을 추가해주세요.");
			return false;
		}
		if(!price || isNaN(price)) {
			alert("가격을 입력 후 상품을 추가해주세요.");
			return false;
			
			
		}
		if(!stock || isNaN(stock)) {
			alert("재고 수량을 입력 후 상품을 추가해주세요.");
			return false;
		}
		
		return true;
	}
	
	function validateSelected() {
	    const checkboxes = document.querySelectorAll('.product_checkbox:checked');
	    if (checkboxes.length === 0) {
	        alert('최소 한 개의 상품을 선택하세요.');
	        return false;
	    }
	    return true;
	}
	

	function toggleAllCheckboxes(source) {
	    const checkboxes = document.getElementsByClassName('product_checkbox');
	    for (let checkbox of checkboxes) {
	        checkbox.checked = source.checked;
	    }
	}
</script>
<title>상품 관리 어드민 페이지</title>
</head>
<body>
	<%@ include file="../nav.jsp"%>
	<div id="adminContainer">
		<h1>상품 관리</h1>
		<form id="productForm" method="POST" enctype="multipart/form-data">
			<div id="selectedActionContainer">
				<input class="submitButton" type="button" onclick="submitForm('create')" name="action" value="상품 추가" />
				<input class="submitButton" type="button" onclick="submitForm('update')" name="action" value="선택 수정" />
				<input class="submitButton" type="button" onclick="submitForm('delete')" name="action" value="선택 삭제" />
			</div>
			<div id="hr"></div>
			<div id="formContainer">
				<div id="labelContainer">
					<span> <input type="checkbox" name="selected" onclick="toggleAllCheckboxes(this)" />
					</span> <span>상품코드</span> <span>이미지</span> <span>설명</span> <span>상품명</span>
					<span>판매가격</span> <span>재고</span> <span>리뷰수</span> <span>평점</span>
					<span>인원</span> <span>음식 종류</span>
				</div>
				<div id="formWrapper">
					<div>추가</div>
					<div>&nbsp;</div>
					<div>
						<input type="file" id="newThumb" name="newThumb" />
					</div>
					<div>
						<textarea id="newDescription" name="newDescription" rows="3"></textarea>
					</div>
					<div>
						<input type="text" id="newName" name="newName" />
					</div>
					<div>
						<input type="text" id="newPrice" type="number" name="newPrice" />
					</div>
					<div>
						<input type="text" id="newStock" type="number" name="newStock" />
					</div>
					<div>0</div>
					<div>0</div>
					<div>
						<select name="newFoodTypeCategory">
							<option value="1">한식</option>
							<option value="2">양식</option>
							<option value="3">중식</option>
							<option value="4">분식</option>
						</select>
					</div>
					<div>
						<select name="newPeopleCategory">
							<option value="1">1~2인용</option>
							<option value="2">3~4인용</option>
						</select>
					</div>
				</div>

				<c:forEach var="product" items="${productList}">
					<div id="formWrapper">
						<div>
							<input type="checkbox" class="product_checkbox" name="checked_${product.id}" value="${product.id}"/>
						</div>
						<div>
							<input type="text" id="id" name="id_${product.id}" value="${product.id}" readonly />
						</div>
						<div>
							<input type="file" id="thumb" name="thumb_${product.id}" accept="image/*" value="${product.thumb}" required />
						</div>
						<div>
							<textarea id="description" name="description_${product.id}" rows="3">${product.description}</textarea>
						</div>
						<div>
							<input type="text" id="name" name="name_${product.id}" value="${product.name}" />
						</div>
						<div>
							<input type="text" id="price" name="price_${product.id}" value="${product.price}원" />
						</div>
						<div>
							<input type="text" id="stock" name="stock_${product.id}" value="${product.stock}" />
						</div>
						<div>${product.totalReview}</div>
						<div>${product.averageReview}</div>
						<div>
							<select name="foodTypeCategory_${product.id}">
								<option value="1"
									<c:if test="${product.categoryType eq 1}">selected</c:if>>한식</option>
								<option value="2"
									<c:if test="${product.categoryType eq 2}">selected</c:if>>양식</option>
								<option value="3"
									<c:if test="${product.categoryType eq 3}">selected</c:if>>중식</option>
								<option value="4"
									<c:if test="${product.categoryType eq 4}">selected</c:if>>분식</option>
							</select>
						</div>
						<div>
							<select name="peopleCategory_${product.id}">
								<option value="1"
									<c:if test="${product.categoryPerson eq 1}">selected</c:if>>1~2인용</option>
								<option value="2"
									<c:if test="${product.categoryPerson eq 2}">selected</c:if>>3~4인용</option>
							</select>
						</div>
					</div>
				</c:forEach>
			</div>
		</form>
	</div>
</body>
</html>
