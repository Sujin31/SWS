<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<% pageContext.setAttribute("CRLF", "\r\n"); %>
<% pageContext.setAttribute("LF", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오늘의 목표</title>
<link  rel="stylesheet" href="/css/board.css" type="text/css">
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script>
function selectDate(){
	var link = "/member/todo?selDate="+$('#currentDate').val();
	location.href=link;
}

function editerMode(i){
	//console.log(i-1);
	$('.editer').eq(i-1).css("display","block");
	$('.viewer').eq(i-1).css("display","none");
}

function validateForm(form){
	if(!form.content.value){
		alert("목표를 입력해주세요.");
		form.content.focus();
		return false;
	}
}
</script>
</head>
<body>

	<div id="page">
	
	<header>
		<jsp:include page="../0_common/Header.jsp"/>
	</header>
	<div id="logout">
		${UserId } 님 <button onclick="location.href='./logout'">로그아웃</button>
	</div>
	<nav class="clearfix"><jsp:include page="../0_common/Nav.jsp"/></nav>
	
	<div class="tododiv">
		<div class="page-title table1200">
	        <div class="container">
	        	<h3>To-Do List</h3>
	        	<hr>
	        </div>
	    </div>
	    
	    <div class="date">
	    	<a href="/member/todo?selDate=${yester }"> &nbsp; < &nbsp; </a>
	    	<input type="date" id="currentDate" value="${selDate }" onchange="selectDate();">
	    	<a href="/member/todo?selDate=${tomorrow }"> &nbsp; > &nbsp; </a>
	    </div>
	    
	    <div class="todobox">
	    	<table class="board-table table1200">
	    		<colgroup>
	    			<col style="width:5%">
	    			<col style="width:*">
	    			<col style="width:20%">
	    		</colgroup>
	    		
	    		<c:choose>
	    			<c:when test="${empty list }">
	    				<tr><td colspan="2">오늘의 목표가 없습니다.</td></tr>
	    			</c:when>
	    			
	    			<c:otherwise>
	    				<c:forEach items="${list }" var="row" varStatus="loop">
	    					<tr>
				    			<td>${loop.count }</td>
				    			<td>
				    				<div class="viewer">
					    				<c:if test="${row.isdone eq 'Y' }"><del>${fn:replace(fn:replace(fn:escapeXml(row.content), CRLF, '<br/>'), LF, '<br/>')} </del></c:if>
					    				<c:if test="${row.isdone eq 'N' }">${fn:replace(fn:replace(fn:escapeXml(row.content), CRLF, '<br/>'), LF, '<br/>')} </c:if>
				    				</div>
				    				<div class="editer">
				    					<form name="editform" action="/member/todo" method="post" onsubmit="return validateForm(this);">
				    						<input type="hidden" name="mode" value="edit">
				    						<input type="hidden" name="idx" value="${row.idx }">
				    						<input type="text" class="input" name="content" value="${fn:replace(fn:replace(fn:escapeXml(row.content), CRLF, '<br/>'), LF, '<br/>')}">
				    						<button class="btn btn-puple" type="submit">저장</button>
				    					</form>
				    				</div>
								</td>
				    			<td>
				    				<c:if test="${row.isdone eq 'Y' }"> 
				    					<button class="btn btn-blue" onclick="location.href='/member/todo?selDate=${selDate}&mode=done&idx=${row.idx }&done=N';">미완</button> 
				    				</c:if>
				    				<c:if test="${row.isdone eq 'N' }"> 
				    					<button class="btn btn-blue" onclick="location.href='/member/todo?selDate=${selDate}&mode=done&idx=${row.idx }&done=Y';">완료</button> 
				    				</c:if>
				    				
				    				<button class="btn btn-puple" onclick="editerMode(${loop.count});">수정</button>
				    				<button class="btn btn-dark" onclick="location.href='/member/todo?selDate=${selDate}&mode=del&idx=${row.idx }';">삭제</button>
				    			</td>
				    		</tr>
	    				</c:forEach>
	    			</c:otherwise>
	    		</c:choose>
	    		
	    		
	    	</table>
	    </div>
	    
	    <div class="inputbox table1200">
	    	<form action="/member/todo" method="post" onsubmit="return validateForm(this);">
	    		<input type="hidden" name="mode" value="save">
	    		<input type="hidden" id="inputdate" name="date" value="${selDate }">
	    		<table class="board-table table1200">
	    			<tr>
	    				<th colspan="2" id="title">To-do 추가</th>
	    			</tr>
	    			<tr>
	    				<td><input type="text" class="input" name="content" value="" style="width: 100%;"></td>
	    				<td style="width:10%;"><button class="btn btn-blue">추가</button></td>
	    			</tr>
	    		</table>
	    	</form>
	    </div>
	</div>
	
	
	<footer><jsp:include page="../0_common/Footer.jsp"/></footer>
	
	</div>

</body>
</html>