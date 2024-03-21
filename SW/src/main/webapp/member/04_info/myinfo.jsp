<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내정보</title>
<link  rel="stylesheet" href="/css/board.css" type="text/css">
<script type="text/javascript">
function validateForm(form){

	if(form.pw.value == ""){
		alert("비밀번호를 입력해주세요.");
		form.pw.focus();
		return false;
	}
	if(form.phone.value == ""){
		alert("전화번호를 입력해주세요.");
		form.phone.focus();
		return false;
	}
	if(form.phone.value.length != 13){
		alert("전화번호에 하이픈(-)을 넣어서 입력해주세요.");
		form.phone.focus();
		return false;
	}


	//비밀번호 변경 시
	if(form.pwcng.value != ""){
		console.log(form.pwcng.value.length > 3);
		if(form.pwcng.value.length < 4){
			alert("변경할 비밀번호는 4자리 이상이어야 합니다.");
			return false;
		}
		if(!pwCheck(form)){
			alert("변경할 비밀번호와 비밀번호 확인이 다릅니다.");
			return false;
		}
	}
	
	return true;
}

function pwCheck(form){
	if(form.pwcheck.value != ""){
		if(form.pwcng.value != form.pwcheck.value){
			return false;
		}
	}
	return true;
}

</script>
</head>
<body>
	<div id="page">
		<header>
			<jsp:include page="../0_common/Header.jsp"/>
		</header>
		
		<div id="logout">
			${UserId } 님 <button onclick="location.href='./logout'">로그아웃</button>
		</div>
		
		<nav>
			<jsp:include page="../0_common/Nav.jsp"/>
		</nav>
		
		
		
		<div class="myinfo">
			<div class="page-title table900">
		        <div class="container">
		        	<h3>내정보</h3>
		        	<hr>
		        </div>
		    </div>
			<form action="/member/myinfo" method="post" onsubmit="return validateForm(this);">
			<input type="hidden" name="id" value="${member.id }">
				<table class="board-table table900">
					<tr>
						<td>아이디</td>
						<td>${member.id }</td>
					</tr>
					<tr>
						<td>이름</td>
						<td>${member.name }</td>
					</tr>
					<tr>
						<td>등급</td>
						<td>
							<select name="level">
								<option value="M_el" <c:if test="${member.auth_level_fk eq 'M_el' }"> selected</c:if> >초등부</option>
								<option value="M_md" <c:if test="${member.auth_level_fk eq 'M_md' }"> selected</c:if> >중등부</option>
								<option value="M_hg" <c:if test="${member.auth_level_fk eq 'M_hg' }"> selected</c:if> >고등부</option>
								<option value="M_ad" <c:if test="${member.auth_level_fk eq 'M_ad' }"> selected</c:if> >성인부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>전화번호</td>
						<td><input type="text" name="phone" class="input" placeholder="000-0000-0000" value="${member.phone }"></td>
					</tr>
					<tr>
						<td>성별</td>
						<td>
							<c:if test="${member.gender eq 'F' }"> 여 </c:if>
							<c:if test="${member.gender eq 'M' }"> 남 </c:if>
						</td>
					</tr>
					<tr>
						<td>생년월일</td>
						<td>${member.birth }</td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="pw" class="input"></td>
					</tr>
					<tr>
						<td>변경할 비밀번호</td>
						<td><input type="password" name="pwcng" class="input"></td>
					</tr>
					<tr>
						<td>비밀번호 확인</td>
						<td><input type="password" name="pwcheck" class="input" ></td>
					</tr>
					<tr>
						<td colspan="2"><button class="btn btn-dark" type="submit" class="input" >저장</button></td>
					</tr>
				</table>
			</form>
		</div>
		
		
		
		<footer>
			<jsp:include page="../0_common/Footer.jsp"/>
		</footer>
		
	</div>
	
</body>
</html>