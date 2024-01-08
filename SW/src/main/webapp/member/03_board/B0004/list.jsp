<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script>
	function OverplayerCheck(idx,link,curNum,pass){
		if(pass > 0){
			var passck = CheckPass(idx);
			if(passck == "false"){
				alert("비밀번호가 틀렸습니다.");
				return false;
			}
		}
		if(curNum == 2){
			alert("인원 초과");
			return false;
		}else{
			location.href=link;
		}
	}
	
	function CheckPass(idx){
		var inputPW = prompt("비밀번호를 입력하세요.");
		var rs = "false";
		
		$.ajax({
			url : "./chat",
			type : "post",
			async: false, 
			data : {"mode" : "checkPw", "idx" : idx ,"pass" : inputPW},
			dataType : "text",
			success : function(result){
				rs=result;
			}
		});
		return rs;
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
                    		<option value="초등" <c:if test="${param.searchStudent eq '초등'}">selected</c:if>>초등</option>
                    		<option value="중등" <c:if test="${param.searchStudent eq '중등'}">selected</c:if>>중등</option>
                    		<option value="고등" <c:if test="${param.searchStudent eq '고등'}">selected</c:if>>고등</option>
                    		<option value="성인" <c:if test="${param.searchStudent eq '성인'}">selected</c:if>>성인</option>
                    	</select>
                    	<select name="searchSubject">
                    		<option value="">선택</option>
                    		<option value="국어" <c:if test="${param.searchSubject eq '국어'}">selected</c:if>>국어</option>
                    		<option value="수학" <c:if test="${param.searchSubject eq '수학'}">selected</c:if>>수학</option>
                    		<option value="영어" <c:if test="${param.searchSubject eq '영어'}">selected</c:if>>영어</option>
                    		<option value="과학" <c:if test="${param.searchSubject eq '과학'}">selected</c:if>>과학</option>
                    		<option value="사회" <c:if test="${param.searchSubject eq '사회'}">selected</c:if>>사회</option>
                    		<option value="기타" <c:if test="${param.searchSubject eq '기타'}">selected</c:if>>기타</option>
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
                		<c:when test="${empty chatList}">
                			<tr>
                				<td colspan="5" align="center">개설된 채팅방이 없습니다.</td>
                			</tr>
                		</c:when>
                		<c:otherwise>
		                	<c:forEach items="${chatList }" var="row" varStatus="loop">
								<tr>
				                    <td>${row.id }</td>
				                    <td>${row.fcate }>${row.scate }</td>
				                    <th style="text-align: center">
				                    	<a href='javascript:void(0);' onclick="OverplayerCheck(${row.id },'./board?cate=${MenuDto.code}&mode=v&idx=${row.id }',${row.player },${fn:length(row.pass)})">${row.name }</a>
				                      	<c:if test="${!empty row.pass}"> 열쇠</c:if>
				                    </th>
				                    <td>${row.player } / 2</td>
				                    <td>${row.owner }</td>
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