<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<script type="text/javascript">
function validateForm(form){
	if(!form.id.value){
		alert("아이디를 입력해주세요.");
		form.id.focus();
		return false;
	}
	if(!form.name.value){
		alert("이름을 입력해주세요.");
		form.name.focus();
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
		<p>비밀번호 찾기</p>
	    <form name="frm" method="post" action="./findInfo" onsubmit="return validateForm(this);">
	    <input type="hidden" name="mode" value="pw">
	    	<table>
	    		<colgroup>
	    			<col width="80px">
	    			<col width="*">
	    		</colgroup>
	    		<tr>
		    		<td>아이디 : </td>
		    		<td><input type="text" placeholder="아이디" name="id" class="in"> </td>
		    	</tr>
		    	<tr>
		    		<td>이름 : </td>
		    		<td><input type="text" placeholder="이름" name="name" class="in"> </td>
		    	</tr>
		        <tr>
		        	<td>전화번호 : </td>
		        	<td><input type="text" placeholder="000-0000-0000" class="in" name="phone"></td>
	        	</tr>
	        	<tr><td colspan="2"><p id="errorMsg">${ errorMsg }</p></td></tr>
	        </table>
	        <button type="submit" id="btn">찾기</button>
	        <button type="button" id="btn" onclick="location.href='./login'" class="leftgap5">취소</button>
	    </form>
	</div>
	<p style="text-align: center;"><a href="./findInfo?mode=id">아이디 찾기</a></p>
</body>
</html>