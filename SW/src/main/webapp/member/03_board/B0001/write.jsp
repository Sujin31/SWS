<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
function validateForm(form){
	if(!form.title.value){
		alert("제목을 입력해주세요.");
		form.title.focus();
		return false;
	}
	if(!form.content.value){
		alert("내용을 입력해주세요.");
		form.content.focus();
		return false;
	}
	
}
</script>
</head>
<body>
<div class="page-title">
        <div class="container">
        	<h3>${MenuDto.name }</h3>
        	<hr>
        	<h4>
	        	<c:choose>
	        		<c:when test="${top[0] ne null and top[1] eq null}">
	        			${top[0] }
	        		</c:when>
	        		<c:when test="${top[0] ne null and top[1] ne null}">
	        			${top[0] } > ${top[1] }
	        		</c:when>
	        	</c:choose>
            </h4>
        </div>
    </div>
   
  <!-- board list area -->
    <div id="board-list">
    	<form action="./boardTmp" name="frm" method="post" onsubmit="return validateForm(this);">
    		<input type="hidden" name="mode" value="w">
       		<input type="hidden" name="menucode" value="${MenuDto.code }">
       		<input type="hidden" name="id" value="${id }">
        	<div class="container">
	            <table class="board-table">
	                <tbody>
		                <tr>
		                    <th scope="col" class="th-num">제목</th>
							<td><input type="text" class="input" name="title" ></td>
		                </tr>
		                <!-- 공지게시판만(관리자) -->
		                <c:if test="${MenuDto.code eq 'menu001' }">
		                <tr>
		                    <th scope="col" class="th-num">필독 여부</th>
							<td><input type="radio" name="notice" value="Y">&nbsp;Y&nbsp;&nbsp;&nbsp;<input type="radio" name="notice" value="N" checked>&nbsp; N  </td>
		                </tr>
		                </c:if>
		                <tr>
		                    <th scope="col" class="th-num">내용</th>
							<td><textarea class="txtarea" name="content"></textarea></td>
		                </tr>
		                <tr>
		                    <th scope="col" class="th-num">첨부파일</th>
							<td><input type="file" class="input" name="file" ></td>
		                </tr>
	                </tbody>
	            </table>
			</div>
			<div class="divdtn">
		    	<button class="btn btn-dark" type="submit">저장</button>
		   		<button type="button" class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=l'">목록</button>
			</div>
 		</form>
    </div>
</body>
</html>