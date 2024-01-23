<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SWS</title>
<link  rel="stylesheet" href="/css/board.css" type="text/css">
</head>
<body>
<div id="page">
	<header>
		<jsp:include page="../0_common/Header.jsp"/>
	</header>
	
	<div id="logout">
		${UserId } 님 <button onclick="location.href='./logout'">로그아웃</button>
	</div>
	
	<nav>
		<jsp:include page="../0_common/Nav.jsp"/>
	</nav>
	<section class="info_main">
		<jsp:include page="../0_common/TodayPlan.jsp"/>
	</section>
	<section>
		<c:import url="/member/boardTmp">
			<c:param name="cate" value="${param.cate }"></c:param>
		</c:import>
	</section>
	<footer>
		<jsp:include page="../0_common/Footer.jsp"/>
	</footer>
	
</div>

</body>
</html>