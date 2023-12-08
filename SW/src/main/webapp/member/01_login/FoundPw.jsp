<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<script type="text/javascript">
function validateForm(form){
	if(!form.pw.value){
		alert("비밀번호를 입력해주세요.");
		form.pw.focus();
		return false;
	}
	if(!pwCheck()){
		alert("비밀번호와 비밀번호 확인 다름니다.");
		return false;
	}
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
            margin-top : 300px;
            width: 300px;
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
</style>
</head>
<body>
	<div>
		<p>비밀번호 재설정</p>
	    <form name="frm" method="post" action="./findInfo" onsubmit="return validateForm(this);">
	    <input type="hidden" name="mode" value="resetPw">
	    <input type="hidden" name="id" value="${ id }">
	    	<table>
	    		<colgroup>
	    			<col width="80px">
	    			<col width="*">
	    		</colgroup>
	    		<tr>
		    		<td>비밀번호 : </td>
		    		<td><input type="password" placeholder="비빌번호" name="pw" class="in"> </td>
		    	</tr>
		    	<tr>
		    		<td>비밀번호 확인 : </td>
		    		<td><input type="password" placeholder="비빌번호 확인" name="pwcheck" class="in"> </td>
		    	</tr>
	        	<tr><td colspan="2"><p id="errorMsg">${ errorMsg }</p></td></tr>
	        </table>
	        <button type="submit" id="btn">변경</button>
	        <button type="button" id="btn" onclick="location.href='./login'" class="leftgap5">취소</button>
	    </form>
	</div>
	<p style="text-align: center;"><a href="./findInfo?mode=id">아이디 찾기</a></p>
</body>
</html>