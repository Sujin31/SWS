<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
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

    <!-- board seach area -->
    <div id="board-search">
        <div class="container">
            <div class="search-window">
                <form action="">
                    <div class="search-wrap">
                        <label for="search" class="blind">내용 검색</label>
                        <input id="search" type="search" name="" placeholder="검색어를 입력해주세요." value="">
                        <button type="submit" class="btn btn-dark">검색</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
   
  <!-- board list area -->
    <div id="board-list">
        <div class="container">
            <table class="board-table">
                <thead>
                <tr>
                    <th scope="col" class="th-num">번호</th>
                    <th scope="col" class="th-title">제목</th>
                    <th scope="col" class="th-date">작성자</th>
                    <th scope="col" class="th-date">등록일</th>
                    <th scope="col" class="th-num">조회수</th>
                </tr>
                </thead>
                <tbody>
					<c:choose>
                		<c:when test="${empty boardLists and empty mustBoardLists}">
                			<tr>
                				<td colspan="6" align="center">등록된 게시물이 없습니다.</td>
                			</tr>
                		</c:when>
                		<c:otherwise>
		                	<c:forEach items="${boardLists }" var="row" varStatus="loop">
								<tr>
				                    <td>${loop.count }</td>
				                    <th>
				                      <a href="./board?cate=${MenuDto.code}&mode=v&idx=${row.idx }">${row.title }</a> <c:if test="${row.isfile eq 'Y' }"> file</c:if>
				                    </th>
				                    <td>${row.id }</td>
				                    <td>${row.regidate }</td>
				                    <td>${row.views }</td>
								</tr>
			            	</c:forEach>
	               		</c:otherwise>
	            	</c:choose>
                </tbody>
            </table>
            <div class="divdtn">
	            <!-- 게시판/회원만 -->
	            <c:if test="${id eq 'admin'}"><button class="btn btn-dark" type="button" onclick="location.href='./board?cate=${MenuDto.code}&mode=w'">작성</button></c:if>
            </div>
        </div>
    </div>
</body>
</html>