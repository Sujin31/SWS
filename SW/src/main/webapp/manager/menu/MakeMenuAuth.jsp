<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴 권한 추가</title>
<link  rel="stylesheet" href="/css/manager.css" type="text/css">
<script type="text/javascript">
	function edit(code, name){
		var url = "./editMenuAuth?code="+code+"&name="+name;
        var name = "edit";
        var option = "width = 1200, height = 500, top = 100, location = no"
        window.open(url, name, option);
	}
</script>
</head>
<body>

	<h2>메뉴 권한 추가하기</h2>
	<div><button onclick="location.href='./main'">메인</button></div>
	<table>
		<colgroup>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
			<col></col>
		</colgroup>
		<tr>
			<th>NUM</th>
			<th>LEV1</th><th>code1</th>
			<th>LEV2</th><th>code2</th>
			<th>LEV3</th><th>code3</th>
			<th>권한</th>
		</tr>
		<c:choose>
			<c:when test="${empty dto }">
				<tr>
					<td colspan="8" align="center">등록된 메뉴가 없습니다.</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${dto }" var="row" varStatus="loop">
					<tr>
						<td>${loop.count }</td>
						<td>${row.name1 }</td>
						<td>${row.code1 }</td>
						<td>${row.name2 }</td>
						<td>${row.code2 }</td>
						<td>${row.name3 }</td>
						<td>${row.code3 }</td>
						<td><button onclick="edit('${row.lcode}','${row.lname }');">수정</button></td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
</body>
</html>