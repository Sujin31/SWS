<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<link  rel="stylesheet" href="/SW/css/manager.css" type="text/css">
<script type="text/javascript">
</script>
</head>
<body>
<h2>${param.name }</h2>
<form action="./editMenuAuth" method="post">
	<input type="hidden" name="menu_fk" value="${param.code }">
	<input type="hidden" name="length" value="${fn:length(list)}">
	<table>
		<colgroup>
			<col style="width:40%">
			<col style="width:*">
			<col style="width:*">
			<col style="width:*">
			<col style="width:*">
		</colgroup>
		<tr>
			<th>등급명</th><th>읽기</th><th>쓰기</th><th>답글</th><th>댓글</th>
		</tr>
		<c:choose>
			<c:when test="${empty list }">
				<tr>
					<td colspan="5">등급을 설정해주세요</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="row" varStatus="loop">
					<tr>
						<td>
							<input type="hidden" name="auth${loop.count }" value="${row.auth_level_fk }">
							${row.name }
							<c:if test="${row.ismng eq 'Y' }">(M)</c:if>
						</td>
						<td>
							<input type="radio" name="read${loop.count }" value="Y" <c:if test="${row.mread eq 'Y'}">checked</c:if>>Y 
							<input type="radio" name="read${loop.count }" value="N" <c:if test="${row.mread eq 'N' or row.mread eq null}">checked</c:if>>N 
						</td>
						<td>
							<input type="radio" name="write${loop.count }" value="Y" <c:if test="${row.mwrite eq 'Y'}">checked</c:if>>Y 
							<input type="radio" name="write${loop.count }" value="N" <c:if test="${row.mwrite eq 'N' or row.mwrite eq null}">checked</c:if>>N 
						</td>
						<td>
							<input type="radio" name="comment${loop.count }" value="Y" <c:if test="${row.mcomment eq 'Y'}">checked</c:if>>Y 
							<input type="radio" name="comment${loop.count }" value="N" <c:if test="${row.mcomment eq 'N' or row.mcomment eq null}">checked</c:if>>N 
						</td>
						<td>
							<input type="radio" name="reply${loop.count }" value="Y" <c:if test="${row.reply eq 'Y'}">checked</c:if>>Y 
							<input type="radio" name="reply${loop.count }" value="N" <c:if test="${row.reply eq 'N' or row.reply eq null}">checked</c:if>>N 
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
	<div><button type="submit">dd</button></div>
</form>
</body>
</html>