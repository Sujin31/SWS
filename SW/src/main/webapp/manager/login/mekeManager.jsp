<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 추가</title>
<link  rel="stylesheet" href="/css/manager.css" type="text/css"/>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script type="text/javascript">
function validateForm(form){
	if(!form.id.value){
		alert("아이디를 입력해주세요.");
		form.id.focus();
		return false;
	}
	if(!idCheck()){
		alert("이미 존재하는 아이디 입니다.");
		return false;
	}
	if(!form.pw.value){
		alert("비밀번호를 입력해주세요.");
		form.pw.focus();
		return false;
	}
	if(!form.name.value){
		alert("이름을 입력해주세요.");
		form.name.focus();
		return false;
	}
	if(!form.birth.value){
		alert("생년월일을 입력해주세요.");
		form.birth.focus();
		return false;
	}
	if(form.birth.value.length != 10){
		alert("생년월일에 하이픈(-)을 넣어서 입력해주세요.");
		form.birth.focus();
		return false;
	}
	if(!form.phone.value){
		alert("전화번호를 입력해주세요.");
		form.phone.focus();
		return false;
	}
	if(form.phone.value.length != 13){
		alert("전화번호에 하이픈(-)을 넣어서 입력해주세요.");
		form.phone.focus();
		return false;
	}
	
}

function idCheck(){
	var form = document.frm;
	var flag = false;

	$.ajax({
		url : "../member/signUp",
		type : "post",
		async: false, 
		data : {"mode" : 1, "check_id" : form.id.value},
		dataType : "text",
		success : function(result){
			console.log("결과는 "+result);
			if(result == "T"){
				//alert("사용가능한 아이디 입니다.");
				flag = true;
			}else{
				//alert("이미 존재하는 아이디 입니다.");
			}
		}
	});
	return flag;
}
</script>
</head>
<body>
	<h2>관리자 추가하기</h2>
	<div><button onclick="location.href='./main'">메인</button></div>
	<form method="post" action="./makemanager" onsubmit="return validateForm(this);" name="frm">
		<table class="writetb" style="width:40%;">
			<tr>
				<th>ID</th>
				<td style="border-top: 1px solid #c6c9cc;">
					<input type="text" name="id" >
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td style="border-top: 1px solid #c6c9cc;">
					<input type="password" name="password" >
				</td>
			</tr>
			<tr>
				<th>권한</th>
				<td style="border-top: 1px solid #c6c9cc;">
					<select name="auth">
						<c:forEach items="${auth }" var="row" varStatus="loop">
							<option value="${row.idx }">${row.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td style="border-top: 1px solid #c6c9cc;">
					<input type="text" name="name" >
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td style="border-top: 1px solid #c6c9cc;">
					<input type="date" name="birth" >
				</td>
			</tr>
			<tr>
				<th>성별</th>
				<td style="border-top: 1px solid #c6c9cc;">
					<input type="radio" name="gender" value="F" checked> 여
					<input type="radio" name="gender" value="M"> 남
				</td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td style="border-top: 1px solid #c6c9cc;">
					<input type="text" name="phone" placeholder="000-0000-0000">
				</td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit">추가</button></td>
			</tr>
		</table>
	</form>
	<br><br>
	<table class="writetb">
		<tr>
			<th>ID</th> <th>권한코드</th> <th>이름</th> <th>전화번호</th><th></th>
		</tr>
		<c:forEach items="${list }" var="row" varStatus="loop">
			<tr>
				<td>${row.id }</td> <td>${row.auth_level_fk }</td> <td>${row.name }</td> <td>${row.phone }</td> 
				<td>
					<c:if test="${row.id ne'admin' }">
						<button type="button" onclick="location.href='./makemanager?del=Y&id=${row.id}'">삭제</button>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>