<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
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
	
	#btn {
            background-color: #808080;
            color: white;
            width: 100px;
            padding: 10px;
            box-sizing: border-box;
            border-radius: 5px;
            border: none;
	}

	.leftgap5{
		margin-left: 50px;
	}
</style>
</head>
<body>
	<div>
		<p>아이디 찾기</p>
		<p>회원님의 아이디는<br><strong>${id }</strong>입니다.</p>
		<button type="button" id="btn" onclick="location.href='./login.do'">로그인</button>
		<button type="button" id="btn" onclick="location.href='./findInfo.do?mode=pw'" class="leftgap5">비밀번호 찾기</button>
	</div>
</body>
</html>