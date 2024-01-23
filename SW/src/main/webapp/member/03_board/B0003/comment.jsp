<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link  rel="stylesheet" href="/css/comment.css" type="text/css">
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script type="text/javascript">
function addComment_atag(n,i){
	var name = "#add_comment"+n+"_"+i;
	$(name).css('display','block');
}
function editComment_atag(n,i){
	$('#edit_comment'+n+"_"+i).css('display','block');
	$('#view'+n+"_"+i).css('display','none');
}
function authCheck(){
	if("${authdto.mcomment}" == "N"){
		alert("권한이 없습니다.");
		return false;
	}
}
</script>
</head>
<body>
	<div id="board-list">
        <div class="container">
        	<form action="./comment" method="post" onsubmit="return authCheck();">
        		<input type="hidden" name="mode" value="write">
        		<input type="hidden" name="id" value="${UserId }">
        		<input type="hidden" name="board_idx" value="${dto.idx }">
				<input type="hidden" name="answer_idx" value="${param.answer_idx }">
        		<input type="hidden" name="menu" value="${dto.menu_fk }">
        		<input type="hidden" name="isanswer" value="Y">
        		<div class="indiv">
        			<textarea class="in" name="comment"></textarea>
	        		<button class="btn btn-dark cmtbtn ">입력</button>
       			</div>
	        </form>
	        <hr>
	        <div class="comment">
	        
       			<c:set var="i" value="1"/>
       			<c:set var="bf" value="0"/>
       			<c:set var="count" value="1"/>
       			<c:set var="edcount" value="1"/>
       			
       			<c:forEach items="${comments }" var="row" varStatus="loop">
       				<c:if test="${row.board_fk eq param.answer_idx }">
       					<c:if test="${loop.count ne 1 and row.idx ne bf }">
	       					<c:set var="i" value="1"/>
	       					<c:set var="bf" value="${row.idx }"/>
       					</c:if>
       				
	       				<c:if test="${i == 1 }">
	       					<div class="main">
	       						<div class="view" id="view${param.answer_idx }_${edcount }">
		        					<div class="info">
					        			${row.id }
						        		<span class="date">(${row.regidate })</span>	
						        		<span class="add"><button type="button" class="addBnt" onclick="addComment_atag(${param.answer_idx },${count});">댓글달기</button></span>
						        		<c:if test="${UserId eq row.id }">
						        			<span class="right">
						        				<button type="button" class="addBnt" onclick="editComment_atag(${param.answer_idx },${edcount});">수정</button> 
						        				<button type="button" onclick="location.href='./comment?cate=${dto.menu_fk }&mode=delete&idx=${row.idx }&bidx=${dto.idx }'">삭제</button>
						        			</span>
						        		</c:if>
						        	</div>
						        	<div class="content">
						        		${row.comments }
						        	</div>
						        </div>
						        <div class="edit_comment" id="edit_comment${param.answer_idx }_${edcount }">
						        	<form action="./comment" method="post">
						        		<input type="hidden" name="mode" value="edit">
						        		<input type="hidden" name="id" value="${UserId }">
						        		<input type="hidden" name="board_idx" value="${dto.idx }">
						        		<input type="hidden" name="answer_idx" value="${param.answer_idx }">
						        		<input type="hidden" name="menu" value="${dto.menu_fk }">
						        		<input type="hidden" name="idx" value="${row.idx }">
						        		<input type="hidden" name="isanswer" value="Y">
						        		
						        		<div class="eidtDiv">
						        			<textarea class="in" name="comment" >${row.comments }</textarea>
							        		<button class="btn btn-dark cmtbtn ">입력</button>
						       			</div>
							        </form>
							        <c:set var="edcount" value="${edcount+1 }"/>
						        </div>
					        	<div class="add_comment" id="add_comment${param.answer_idx }_${count }" onsubmit="return authCheck();">
					        		<form action="./comment" method="post" name="addfrm">
					        			<input type="hidden" name="mode" value="add">
					        			<input type="hidden" name="id" value="${UserId }">
						        		<input type="hidden" name="board_idx" value="${dto.idx }">
						        		<input type="hidden" name="answer_idx" value="${param.answer_idx }">
						        		<input type="hidden" name="menu" value="${dto.menu_fk }">
						        		<input type="hidden" name="idx" value="${row.idx }">
						        		<input type="hidden" name="isanswer" value="Y">
						        		<textarea class="in" name="comment" ></textarea>
						        		<button class="btn btn-dark cmtbtn">입력</button>
					        		</form>
					        	</div>
					        	<c:set var="i" value="2"/>
					        	<c:set var="bf" value="${row.idx }"/>
					        	<c:set var="count" value="${count+1 }"/>
				        	</div>
	       				</c:if>
	       				<c:if test="${row.idx2 ne 0 }">
	       					<div class="subL">
	       						ㄴ
	       					</div>
	       					<div class="sub">
	       						<div class="view" id="view${param.answer_idx }_${edcount }">
		        					<div class="info">
					        			${row.id2 }
						        		<span class="date">(${row.regidate2 })</span>	
						        		<c:if test="${UserId eq row.id2 }">
						        			<span class="right">
						        				<button type="button" class="addBnt" onclick="editComment_atag(${param.answer_idx },${edcount});">수정</button> 
						        				<button type="button" onclick="location.href='./comment?cate=${dto.menu_fk }&mode=delete&idx=${row.idx2 }&bidx=${dto.idx }'">삭제</button>
						        			</span>
						        		</c:if>
						        	</div>
						        	<div class="content">
						        		${row.comments2 }
						        	</div>
					        	</div>
					        	<div class="edit_comment" id="edit_comment${param.answer_idx }_${edcount }">
						        	<form action="./comment" method="post">
						        		<input type="hidden" name="mode" value="edit">
						        		<input type="hidden" name="id" value="${UserId }">
						        		<input type="hidden" name="board_idx" value="${dto.idx }">
						        		<input type="hidden" name="answer_idx" value="${param.answer_idx }">
						        		<input type="hidden" name="menu" value="${dto.menu_fk }">
						        		<input type="hidden" name="idx" value="${row.idx2 }">
						        		<input type="hidden" name="isanswer" value="Y">
						        		
						        		<div class="eidtDiv">
						        			<textarea class="in" name="comment" >${row.comments2 }</textarea>
							        		<button class="btn btn-dark cmtbtn ">입력</button>
						       			</div>
							        </form>
							        <c:set var="edcount" value="${edcount+1 }"/>
						        </div>
				        	</div>
				        	<c:set var="i" value="2"/>
	       				</c:if>
       				</c:if> <!-- answer_idx -->
	        	</c:forEach>
	        </div>
        </div>
    </div>
</body>
</html>