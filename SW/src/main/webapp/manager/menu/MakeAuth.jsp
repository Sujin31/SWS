<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>권한 추가</title>
<link  rel="stylesheet" href="/css/manager.css" type="text/css">
</head>
<body>

	<h2>권한추가하기</h2>
	<div><button onclick="location.href='./main'">메인</button></div>
	<form method="post" action="./makeauth" onsubmit="">
		<table class="writetb">
			<tr>
				<th>권한명</th> <th>권한코드</th> <th>관리자</th> <th>권한레벨</th>
			</tr>
			<tr>
				<td><input type="text" name="name"></td>
				<td><input type="text" name="idx"></td>
				<td><input type="text" name="ismng"></td>
				<td><input type="number" name="level"></td>
			</tr>
			<tr>
				<td colspan="4"><button type="submit">추가</button></td>
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
		</colgroup>
		<tr>
			<th>권한명</th><th>권한코드</th><th>관리자</th><th>권한레벨</th><th></th>
		</tr>
		<c:choose>
			<c:when test="${empty dto }">
				<tr>
					<td colspan="5" align="center">등록된 권한이 없습니다.</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${dto }" var="row" varStatus="loop">
					<tr>
						<td>${row.name }</td>
						<td>${row.idx }</td>
						<td>${row.ismng}</td>
						<td>${row.level}</td>
						<td><button onclick="location.href='./makeauth?del=Y&idx=${row.idx}'">삭제</button></td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
</body>
</html>