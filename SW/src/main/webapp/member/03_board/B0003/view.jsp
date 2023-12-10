<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
					<td colspan="2" id="title"><span style="font-size: 40px; margin-right:20px; ">Q.</span>${dto.title }
						<div class="rightBtn">
							<button type="button" class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=l'">목록</button>
				            <c:if test="${UserId eq dto.id}">
				            	<button class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=e&idx=${dto.idx }'">수정</button>
				            	<button class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=d&idx=${dto.idx }'">삭제</button>
			            	</c:if>
			            	<c:if test="${UserId ne dto.id and authdto.answer eq 'Y'}">
			            		<button class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=answer&idx=${dto.idx }'">답변작성</button>
			            	</c:if>
		            	</div>
					</td>
				</tr>
				<tr>
					<td id="info">
						${dto.id} | ${dto.regidate } (수정 : ${dto.editdate })
                    </td>
                    <td style="text-align: right;"></td>
				</tr>
				<tr>
					<td colspan="2" id="content" style="text-align: left;">${dto.content }</td>
				</tr>
            </table>
        </div>
    </div>

 	<!-- 답변 게시 -->
 	<c:choose>
	 	<c:when test="${empty answerList }">
	 		없어용
	 	</c:when>
	 	<c:otherwise>
		 	<c:forEach items="${answerList }" var="row" varStatus="loop">
	 			 <div id="" class="gap200">
			        <div class="container">
			            <table class="board-table">
							<tr>
								<td colspan="2" id="title"><span style="font-size: 40px; margin-right:20px; ">A.</span>
									 <!-- 작성자만 -->
									<div class="rightBtn">
							            <c:if test="${UserId eq row.id}">
							            	<button class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=answeredit&boardidx=${dto.idx }&idx=${row.idx }'">수정</button>
							            	<button class="btn btn-dark" onclick="location.href='./answer?cate=${MenuDto.code}&mode=delete&boardidx=${dto.idx }&idx=${row.idx }'">삭제</button>
						            	</c:if>
					            	</div>
				            	</td>
							</tr>
							<tr>
								<td id="info">
									${row.id} | ${row.regidate } <c:if test="${row.editdate ne null }">(수정일자 : ${row.editdate })</c:if>
			                    </td>
			                    <td style="text-align: right;"></td>
							</tr>
							<tr>
								<td colspan="2" id="content" style="text-align: left;">${row.answer }</td>
							</tr>
			            </table>
			        </div>
				</div>
			    <p>댓글</p>
			    <jsp:include page="./comment.jsp">
			    	<jsp:param value="${row.idx }" name="answer_idx"/>
			    </jsp:include>
	 		</c:forEach>
	 	</c:otherwise>
 	</c:choose>
 	
   
    
    
</body>
</html>