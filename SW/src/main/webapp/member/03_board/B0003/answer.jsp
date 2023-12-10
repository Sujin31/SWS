<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script type="text/javascript">
function validateForm(form){
	if(!form.answer.value){
		alert("내용을 입력해주세요.");
		form.answer.focus();
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
   <!-- 질문 게시 -->
    <div id="board-list">
        <div class="container">
            <table class="board-table">
				<tr>
					<td colspan="2" id="title">
						<span style="font-size: 40px; margin-right:20px; ">Q.</span>${dto.title }
					</td>
				</tr>
				<tr>
					<td id="info">
						${dto.id} | ${dto.regidate }
                    </td>
                    <td style="text-align: right;"></td>
				</tr>
				<tr>
					<td colspan="2" id="content" style="text-align: left;">${dto.content }</td>
				</tr>
            </table>
        </div>
   </div>
  <!-- board list area -->
	<div id="board-list">
		<form action="./answer" name="writeform" method="post" onsubmit="return validateForm(this);">
			<div class="container">
			
				<input type="hidden" name="boardTmp" value="${MenuDto.board_tmp }">
	     		<input type="hidden" name="menucode" value="${MenuDto.code }">
	     		<input type="hidden" name="boardidx" value="${dto.idx }">
	     		<input type="hidden" name="id" value="${UserId }">
	     		<input type="hidden" name="mode" value="write">
	           <table class="board-table">
	               <tbody>
	               		<tr>
	               			<td colspan="2" id="title"><span style="font-size: 40px; margin-right:20px; ">A.</span>
	               		</tr>
		                <tr>
		                    <th scope="col" class="th-write">답변</th>
							<td><textarea class="txtarea" name="answer" style="height: 400px"></textarea></td>
		                </tr>
	               </tbody>
	           </table>
           
			</div>
			<div class="divdtn">
		    	<button class="btn btn-dark" type="submit">저장</button>
		   		<button type="button" class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=l'">목록</button>
		   		<button type="button" class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=v&idx=${dto.idx }'">취소</button>
			</div>
		</form>
    </div>
</body>
</html>