<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script type="text/javascript">
function validateForm(form){
	if(!form.id.value){
		alert('아이디를 입력하세요');
		form.id.focus();
		return false;
	}
	if(!form.pw.value){
		alert('비밀번호를 입력하세요');
		form.pw.focus();
		return false;
	}
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
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border-radius: 5px;
            border: none;
	}

	.in {
            margin-bottom: 10px;
	}

	#btn {
            background-color: #808080;
            color: white;
	}

	a {
            text-decoration: none;
            color: #9B9B9B;
	        font-size: 12px;
	}
	
	#errorMsg{
		color : red;
		margin-bottom: 30px;
		font-size: 14px;
	}
</style>
</head>
<body>
	<div>
		<p>로그인</p>
	    <form method="post" action="../member/login.do" onsubmit="return validateForm(this);">
	        <input type="text" placeholder="아이디" class="in" name="id">
	        <input type="password" placeholder="비밀번호" class="in" name="pw">
	        <input type="submit" id="btn" value="로그인" ><br>
	        <p id="errorMsg">${ errorMsg }</p>
	    </form>
	    <a href="./signUp.do" style="margin-right: 20px;">회원가입</a>
	    <a href="./findInfo.do?mode=id">ID/PW 찾기</a>
	</div>
</body>
</html>