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
					<td colspan="2" id="title">
						${fn:replace(fn:replace(fn:escapeXml(dto.title), CRLF, '<br/>'), LF, '<br/>')}
					</td>
				</tr>
				<tr>
					<td id="info">
						${dto.id} | ${dto.regidate }
                    </td>
                    <td style="text-align: right;">
                    	<c:if test="${not empty file.oname }">
							<a href="./download?oname=${file.oname }&sname=${file.sname}&idx=${file.idx}">${file.oname }</a>
						</c:if>
                    </td>
				</tr>
				<tr>
					<td colspan="2" id="content" style="text-align: left;">
						${fn:replace(fn:replace(fn:escapeXml(dto.content), CRLF, '<br/>'), LF, '<br/>')}
					</td>
				</tr>
            </table>
            <div class="divdtn">
            	<button type="button" class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=l'">목록</button>
	            <!-- 작성자만 -->
	            <c:if test="${UserId eq dto.id}">
	            	<button class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=e&idx=${dto.idx }'">수정</button>
	            	<button class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=d&idx=${dto.idx }'">삭제</button>
            	</c:if>
            </div>
        </div>
    </div>
    <jsp:include page="./comment.jsp"/>
    
    
</body>
</html>