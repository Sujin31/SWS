<%@ page import="manage.MenuDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

</head>
<body>
<div id="menu">
	<ul class="dept1">
		<li><a href="./board?cate=menu001&mode=l">공지게시판</a></li>
		<li><a href="#">자유게시판</a>
			<ul class="dept2">
				<li><a href="./board?cate=menu003&mode=l">초등부</a></li>
				<li><a href="./board?cate=menu004&mode=l">중등부</a></li>
				<li><a href="./board?cate=menu005&mode=l">고등부</a></li>
				<li><a href="./board?cate=menu006&mode=l">종합</a></li>
			</ul>
		</li>
		<li><a href="#">QnA게시판</a>
			<ul class="dept2">
				<li><a href="#">초등부</a>
					<ul class="dept3">
						<li><a href="./board?cate=menu009&mode=l">국어</a></li>
						<li><a href="./board?cate=menu010&mode=l">수학</a></li>
						<li><a href="./board?cate=menu011&mode=l">영어</a></li>
						<li><a href="./board?cate=menu012&mode=l">기타</a></li>
					</ul>
				</li>
				<li><a href="#">중등부</a>
					<ul class="dept3">
						<li><a href="./board?cate=menu014&mode=l">국어</a></li>
						<li><a href="./board?cate=menu015&mode=l">수학</a></li>
						<li><a href="./board?cate=menu016&mode=l">영어</a></li>
						<li><a href="./board?cate=menu017&mode=l">과학</a></li>
						<li><a href="./board?cate=menu019&mode=l">사회</a></li>
						<li><a href="./board?cate=menu020&mode=l">기타</a></li>
					</ul>
				</li>
				<li><a href="#">고등부</a>
					<ul class="dept3">
						<li><a href="./board?cate=menu021&mode=l">국어</a></li>
						<li><a href="./board?cate=menu022&mode=l">수학</a></li>
						<li><a href="./board?cate=menu023&mode=l">영어</a></li>
						<li><a href="./board?cate=menu024&mode=l">과학</a></li>
						<li><a href="./board?cate=menu025&mode=l">사회</a></li>
						<li><a href="./board?cate=menu026&mode=l">기타</a></li>
					</ul>
				</li>
			</ul>
		</li>
		<li><a href="./board?cate=menu027&mode=l">1:1 과외</a></li>
		<li><a href="#">스터디게시판</a>
			<ul class="dept2">
				<li><a href="./board?cate=menu029&mode=l">웹&네트워크</a></li>
				<li><a href="./board?cate=menu030&mode=l">자바&스프링</a></li>
				<li><a href="./board?cate=menu031&mode=l">서버</a></li>
				<li><a href="./board?cate=menu032&mode=l">오류</a></li>
			</ul>
		</li>
		<c:if test="${Auth ne 'viewer' }">
			<li><a href="./myinfo">내정보</a>
				<ul class="dept2">
					<li><a href="./myinfo">내정보</a></li>
					<li><a href="./todo">오늘의 목표</a></li>
				</ul>
			</li>
		</c:if>
		<c:if test="${Auth eq 'viewer' }">
			<li><a href="#">로그인</a>
				<ul class="dept2">
					<li><a href="./login">로그인</a></li>
					<li><a href="./signUp">회원가입</a></li>
				</ul>
			</li>
		</c:if>
	</ul>
</div>
</body>
</html>