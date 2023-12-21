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
                <form method="get">
                	<input type="hidden" name="cate" value="${MenuDto.code}">
                	<input type="hidden" name="mode" value="l">
                    <div class="search-wrap" style=" max-width: 630px;">
                    	<select name="searchStudent">
                    		<option value="">선택</option>
                    		<option value="el">초등</option>
                    		<option value="md">중등</option>
                    		<option value="hg">고등</option>
                    		<option value="ad">성인</option>
                    	</select>
                    	<select name="searchSubject">
                    		<option value="">선택</option>
                    		<option value="kor">국어</option>
                    		<option value="math">수학</option>
                    		<option value="eng">영어</option>
                    		<option value="sc">과학</option>
                    		<option value="so">사회</option>
                    		<option value="etc">기타</option>
                    	</select>
                        <input id="search" type="text" name="title" placeholder="방이름을 입력해주세요." style="width: 48%;">
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
                    <th scope="col" class="th-num">방번호</th>
                    <th scope="col" class="th-title">카테고리</th>
                    <th scope="col" class="th-date">방이름</th>
                    <th scope="col" class="th-date">참여자 수</th>
                    <th scope="col" class="th-num">방장</th>
                </tr>
                </thead>
                <tbody>
					<c:choose>
                		<c:when test="${empty boardLists and empty mustBoardLists}">
                			<tr>
                				<td colspan="5" align="center">개설된 채팅방이 없습니다.</td>
                			</tr>
                		</c:when>
                		<c:otherwise>
		                	<c:forEach items="${boardLists }" var="row" varStatus="loop">
								<tr>
				                    <td>${row.idx }</td>
				                    <td>${row.student }>${row.subject }</td>
				                    <th>
				                      <a href="./board?cate=${MenuDto.code}&mode=v&idx=${row.idx }">${row.title }</a> <c:if test="${row.isPass eq 'Y' }"> 🔒</c:if>
				                    </th>
				                    <td>${row.curnum } / ${row.allnum }</td>
				                    <td>${row.idx }</td>
								</tr>
			            	</c:forEach>
	               		</c:otherwise>
	            	</c:choose>
                </tbody>
            </table>
            <div class="divdtn">${map.pagingImg }</div>
            <div class="divdtn">
	            <c:if test="${!empty UserId}"><button class="btn btn-dark" type="button" onclick="location.href='./board?cate=${MenuDto.code}&mode=w'">작성</button></c:if>
            </div>
        </div>
    </div>
</body>
</html>