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

    <div id="board-list">
        <div class="container">
            <table class="board-table">
				<tr>
					<td id="title">${dto.title }</td>
				</tr>
				<tr>
					<td id="info">
                    	관리자 | ${dto.regidate }
                    </td>
				</tr>
				<tr>
					<td id="content" style="text-align: left;">${dto.content }</td>
				</tr>
				<tr>
					<td>
						<c:if test="${not empty file.oname }">
							${file.oname }
							<a href="./download?oname=${file.oname }&sname=${file.sname}&idx=${file.idx}">[다운로드]</a>
						</c:if>
					</td>
				</tr>
            </table>
            <div class="divdtn">
            	<button type="button" class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=l'">목록</button>
	            <!-- 작성자만 -->
	            <c:if test="${dto.id eq UserId}">
	            	<button class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=e&idx=${dto.idx }'">수정</button>
	            	<button class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=d&idx=${dto.idx }'">삭제</button>
            	</c:if>
            </div>
        </div>
    </div>
</body>
</html>