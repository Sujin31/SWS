<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
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
		<form action="/member/chat" name="writeform" method="post" onsubmit="return validateForm(this);">
			<div class="container">
			
				<input type="hidden" name="boardTmp" value="${MenuDto.board_tmp }">
	     		<input type="hidden" name="menucode" value="${MenuDto.code }">
	     		<input type="hidden" name="id" value="${UserId }">
	     		<input type="hidden" name="mode" value="create">
	           <table class="board-table">
	               <tbody>
	               		<tr>
		                    <th scope="col" class="th-write">카테고리</th>
							<td>
								<select name="searchStudent">
		                    		<option value="초등">초등</option>
		                    		<option value="중등">중등</option>
		                    		<option value="고등">고등</option>
		                    		<option value="성인">성인</option>
		                    	</select>
		                    	<select name="searchSubject">
		                    		<option value="국어">국어</option>
		                    		<option value="수학">수학</option>
		                    		<option value="영어">영어</option>
		                    		<option value="과학">과학</option>
		                    		<option value="사회">사회</option>
		                    		<option value="기타">기타</option>
		                    	</select>
							</td>
		                </tr>
		                <tr>
		                    <th scope="col" class="th-write">방이름</th>
							<td><input type="text" class="input" name="title" ></td>
		                </tr>
	                 	<tr>
	                 		<th scope="col" class="th-write">비밀번호</th>
		                 	<td>
								<input type="password" class="input" name="password" >
							</td>
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