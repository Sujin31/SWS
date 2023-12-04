<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script type="text/javascript">
function validateForm(form){
	
	if(!form.name.value){
		alert("이름을 입력해주세요.");
		form.name.focus();
		return false;
	}
	if(!form.id.value){
		alert("아이디를 입력해주세요.");
		form.id.focus();
		return false;
	}
	if(!form.pw.value){
		alert("비밀번호를 입력해주세요.");
		form.pw.focus();
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
	if(!idCheck()){
		alert("이미 존재하는 아이디 입니다.");
		return false;
	}
	if(!pwCheck()){
		alert("비밀번호와 비밀번호 확인 다름");
		return false;
	}
}

function idCheck(i){
	var form = document.frm;
	var flag = false;

	$.ajax({
		url : "./signUp.do",
		type : "post",
		async: false, 
		data : {"mode" : 1, "check_id" : form.id.value},
		dataType : "text",
		success : function(result){
			console.log("결과는 "+result);
			if(i == 'b'){
				if(result == "T"){
					alert("사용가능한 아이디 입니다.");
					flag = true;
				}else{
					alert("이미 존재하는 아이디 입니다.");
				}
			}else{
				if(result == "T"){
					flag = true; 
				}
			}
		}
	});
	return flag;
}

function pwCheck(){
	var form = document.frm;
	if(form.pw.value != form.pwcheck.value){
		return false;
	}
	return true;
}
</script>
<style>
       @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap');

* {
           font-family: 'Noto Sans KR', sans-serif;
       }

body{
	background-color: #808080;
}

   div {
           margin: 0 auto;
           margin-top : 200px;
           width: 400px;
           background-color: #EEEFF1;
           border-radius: 5px;
           text-align: center;
           padding: 20px;
}

input {
           margin-left: 10px;
}

.in {
			width: 100%;
           padding: 10px;
           box-sizing: border-box;
           border-radius: 5px;
           border: none;
}

#btn {
           background-color: #808080;
           color: white;
           width: 80px;
           padding: 10px;
           box-sizing: border-box;
           border-radius: 5px;
           border: none;
}

a {
           text-decoration: none;
           color: #9B9B9B;
        font-size: 12px;
}

tr {
	height: 60px;
}

#errorMsg{
	color : red;
	margin-bottom: 30px;
	font-size: 14px;
}

.leftgap{
	margin-left: 10px;
}
.leftgap5{
	margin-left: 50px;
}

#check{
	 background-color: #808080;
     color: white;
     padding: 10px;
     box-sizing: border-box;
     border-radius: 5px;
     border: none;
}
#selbox{
	 	width: 100%;
        padding: 10px;
        box-sizing: border-box;
        border-radius: 5px;
        border: none;
        margin-left: 10px;
        text-align: center;
}
</style>
</head>
<body>
	<div>
		<p>회원가입</p>
	    <form name="frm" method="post" action="./signUp.do" onsubmit="return validateForm(this);">
	    <input type="hidden" name="mode" value="2">
	    	<table>
	    		<colgroup>
	    			<col width="110px">
	    			<col width="*">
	    			<col width="10px">
	    		</colgroup>
		    	<tr>
		    		<td>이름 : </td>
		    		<td><input type="text" placeholder="이름" name="name" class="in"> </td><td></td>
		    	</tr>
		    	<tr>
		        	<td>아이디 : </td>
		        	<td>
		        		<input type="text" placeholder="아이디" class="in" name="id" style="width:65%;">
		        		<button class="leftgap" id="check" type="button" onclick="idCheck('b');">ID체크</button>
		        	</td><td></td>
		        </tr>
		        <tr>
		        	<td>비밀번호 : </td>
		        	<td><input type="password" placeholder="비밀번호" class="in" name="pw"></td><td></td>
		        </tr>
		        <tr>
		        	<td>비밀번호 확인: </td>
		        	<td><input type="password" placeholder="비밀번호 확인" class="in" name="pwcheck"></td><td></td>
		        </tr>
		        <tr>
		        	<td>학급</td>
		        	<td>
		        		<select name="level" id="selbox">
		        			<c:forEach items="${level }" var="row" varStatus="loop">
		        				<option value="${row.idx }">${row.name }</option>
		        			</c:forEach>
		        		</select>
		        	</td>
		        </tr>
		        <tr>
		        	<td>생년월일 : </td>
		        	<td><input type="text" placeholder="yyyy-mm-dd" class="in" name="birth"></td><td></td>
	        	</tr>
		        <tr>
		        	<td>성별 : </td>
		        	<td><input type="radio" name="gender" value="F" checked>여성 <input type="radio" name="gender" value="M" > 남성</td><td></td>
	        	</tr>
	        	<tr>
		        	<td>전화번호 : </td>
		        	<td><input type="text" placeholder="000-0000-0000" class="in" name="phone"></td><td></td>
	        	</tr>
	        </table>
	        <br>
	        <!-- <input type="submit" id="btn" value="가입" ><br> -->
	        <button type="submit" id="btn">가입</button>
	        <button type="button" id="btn" onclick="location.href='./login.do'" class="leftgap5">취소</button>
	    </form>
	    <!-- <a href="#" style="margin-right: 20px;">가입</a>
	    <a href="./login.do">취소</a> -->
	</div>
</body>
</html>