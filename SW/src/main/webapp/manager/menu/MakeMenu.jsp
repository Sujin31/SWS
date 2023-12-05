<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴 추가</title>
<link  rel="stylesheet" href="/SW/css/manager.css" type="text/css">
</head>
<body>

	<h2>메뉴추가하기</h2>
	<div><button onclick="location.href='./main'">메인</button></div>
	<form method="post" action="./makemenu" onsubmit="">
		<table class="writetb">
			<tr>
				<th>메뉴명</th> <th>메뉴코드</th> <th>상위메뉴</th> <th>depts</th> <th>링크</th> <th>템플릿</th> <th>order</th>
			</tr>
			<tr>
				<td><input type="text" name="name"></td>
				<td><input type="text" name="code"></td>
				<td><input type="text" name="pmenu_code"></td>
				<td><input type="number" name="depts"></td>
				<td><input type="text" name="link"></td>
				<td><input type="text" name="tmp"></td>
				<td><input type="number" name="order"></td>
			</tr>
			<tr>
				<td colspan="7"><button type="submit">추가</button></td>
			</tr>
		</table>
	</form>
	<br><br>
	
	<table>
		<colgroup>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
		</colgroup>
		<tr>
			<th>NUM</th>
			<th>LEV1</th><th>code1</th>
			<th>LEV2</th><th>code2</th>
			<th>LEV3</th><th>code3</th>
			<th>link</th>
		</tr>
		<c:choose>
			<c:when test="${empty menulist }">
				<tr>
					<td colspan="8" align="center">등록된 메뉴가 없습니다.</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${menulist }" var="row" varStatus="loop">
					<tr>
						<td>${loop.count }</td>
						<td>${row.name1 }</td>
						<td>${row.code1 }</td>
						<td>${row.name2 }</td>
						<td>${row.code2 }</td>
						<td>${row.name3 }</td>
						<td>${row.code3 }</td>
						<td>${row.link }</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
</body>
</html>