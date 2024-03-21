<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link  rel="stylesheet" href="/css/main_section.css" type="text/css">
</head>
<body>
	<div>
		<div class="gaptop30">
			<table class="board-table">
				<colgroup>
					<col style="width:20%">
					<col style="width:*">
				</colgroup>
				<tr>
					<th colspan="2">공지</th>
				</tr>
				<c:choose>
					<c:when test="${empty mNoticeList and empty noticeList}">
						<tr>
							<td colspan="2">공지가 없습니다.</td>
						</tr>
					</c:when>
					
					<c:otherwise>
						<c:forEach items="${mNoticeList }" var="row" varStatus="loop">
							<tr>
								<td><c:if test="${row.must eq 'Y' }">[필독]</c:if></td>
								<td><a href="/member/board?cate=menu001&mode=v&idx=${row.idx }">${row.title }</a></td>
							</tr>
						</c:forEach>
						<c:forEach items="${noticeList }" var="row" varStatus="loop">
							<tr>
								<td><c:if test="${row.must eq 'N' }"> ${row.idx} </c:if></td>
								<td><a href="/member/board?cate=menu001&mode=v&idx=${row.idx }">${row.title }</a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
					
				</c:choose>
			</table>
		</div>
		
		<div class="gaptop30">
			<table class="board-table">
				<colgroup>
					<col style="width:20%">
					<col style="width:*">
				</colgroup>
				<tr>
					<th colspan="2">내 QnA</th>
				</tr>
				<c:choose>
					<c:when test="${empty myList }">
						<tr>
							<td colspan="2">게시글이 없습니다.</td>
						</tr>
					</c:when>
					
					<c:otherwise>
						<c:forEach items="${myList }" var="row" varStatus="loop">
							<tr>
								<td>${row.menu }>${row.menu2 }</td>
								<td><a href="/member/board?cate=${row.menu_fk }&mode=v&idx=${row.idx }">${row.title }</a> [${row.answers }]</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		
		<div class="gaptop30">
			<table class="board-table">
				<colgroup>
					<col style="width:20%">
					<col style="width:*">
					<col style="width:20%">
				</colgroup>
				<tr>
					<th colspan="3">인기글</th>
				</tr>
				<c:choose>
					<c:when test="${empty topList }">
						<tr>
							<td colspan="3">게시글이 없습니다.</td>
						</tr>
					</c:when>
					
					<c:otherwise>
						<c:forEach items="${topList }" var="row" varStatus="loop">
							<tr>
								<td>${row.menu }</td>
								<td><a href="/member/board?cate=${row.menu_fk }&mode=v&idx=${row.idx }">${row.title }</a></td>
								<td>${row.id }</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>
</body>
</html>