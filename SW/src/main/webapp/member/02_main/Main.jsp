<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link  rel="stylesheet" href="/css/default.css" type="text/css">
</head>
<body>

	<div id="page">
	
	<header><jsp:include page="../0_common/Header.jsp"/></header>
	<div id="logout">
		${UserId } 님 <button onclick="location.href='./logout'">로그아웃</button>
	</div>
	<nav class="clearfix"><jsp:include page="../0_common/Nav.jsp"/></nav>
	
	<section class="info_main"><jsp:include page="../0_common/TodayPlan.jsp"/></section>
	
	<section><jsp:include page="./Main_sec.jsp"/></section>
	
	<footer><jsp:include page="../0_common/Footer.jsp"/></footer>
	
	</div>

</body>
</html>