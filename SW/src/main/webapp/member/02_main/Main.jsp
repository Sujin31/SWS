<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link  rel="stylesheet" href="/css/default.css" type="text/css">
</head>
<body>

	<div id="page">
	
	<header>  <jsp:include page="../0_common/Header.jsp"/>	</header>
	<div id="logout">
		${UserId } 님 
		<c:if test="${Auth ne 'viewer' }"><button onclick="location.href='./logout'">로그아웃</button></c:if>
		<c:if test="${Auth eq 'viewer' }"><button onclick="location.href='./login'">로그인</button></c:if>
	</div>
	<nav class="clearfix"><jsp:include page="../0_common/Nav.jsp"/></nav>
	
	<section class="info_main"><jsp:include page="../0_common/TodayPlan.jsp"/></section>
	
	<section><jsp:include page="./Main_sec.jsp"/></section>
	
	<footer><jsp:include page="../0_common/Footer.jsp"/></footer>
	
	</div>

</body>
</html>