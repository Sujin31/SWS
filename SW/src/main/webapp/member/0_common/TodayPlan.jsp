<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
.today_div{
	width: 90%;
	height: 90%;
	margin: 0 auto;
	margin-top: 5%;
	text-align: center;
	box-sizing: border-box;
	border-radius: 40px 80px / 80px 40px;
	border : 1px solid black;
	padding-top: 50px;
}

.today_tb{
	/*margin-left: 50px;*/
	margin : 0 auto;
	width : 80%;
}

.today_p{
	margin-bottom: 30px;
}

</style>
</head>
<body>
	<div class="today_div">
		<p class="today_p">TODAY PLAN</p>
		<table class="today_tb">
			<colgroup>
				<col width="10%;">
				<col width="*">
				<col width="10%;">
			</colgroup>
			<c:choose>
				<c:when test="${empty todoList }">
					<tr>
						<td colspan="3">오늘 목표가 없어요.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${todoList }" var="row" varStatus="loop">
						<tr>
							<td>${loop.count }</td>
							<td>
								<c:if test="${row.isdone eq 'Y' }"> <del>${row.content }</del></c:if>
								<c:if test="${row.isdone eq 'N' }">${row.content }</c:if>
							</td>
							<td>
								<c:if test="${row.isdone eq 'Y' }"><img src="/resources/done.png" width="20" style="margin-left: 10px;"></c:if>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			
		</table>
		
	</div>
</body>
</html>