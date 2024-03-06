<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
var oEditors = [];
$(function(){
    nhn.husky.EZCreator.createInIFrame({
     oAppRef: oEditors,
     elPlaceHolder: "ir1",
     sSkinURI: "/se2/SmartEditor2Skin.html",
     fCreator: "createSEditor2"
    });
});

function save(){
	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
	document.getElementById('writeform').submit();
}

function validateForm(form){
	if(!form.title.value){
		alert("제목을 입력해주세요.");
		form.title.focus();
		return false;
	}
	if(!form.content.value){
		alert("내용을 입력해주세요.");
		form.content.focus();
		return false;
	}
	if(form.file.value){
		form.isfile.value = "Y";
	}
}

function enterKey(e){

	if(e.code == 'Enter'){
		var tag = $('#inputTag').val();
		$('.tagInput').append("<input type='hidden' name='tag' value='"+tag+"' >");
		$('.tags').append("<span class='tag'>"+tag+"<button type='button' class='tagbutton' onclick='delTag(this)'>x</button> </span>");
		$('#inputTag').val('');
	}
}

function delTag(o){
	var i = $(o).parent().index();
	$('.tagInput input').eq(i).remove();
	$('.tag').eq(i).remove();
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


  <!-- board list area -->
	<div id="board-list">
		<form action="./boardwrite" id="writeform" name="writeform" method="post" enctype="multipart/form-data" onsubmit="return validateForm(this);">
			<div class="container">
			
				<input type="hidden" name="boardTmp" value="${MenuDto.board_tmp }">
	     		<input type="hidden" name="menucode" value="${MenuDto.code }">
	     		<input type="hidden" name="id" value="${UserId }">
	     		<input type="hidden" name="isfile" value="N">
	           <table class="board-table">
	               <tbody>
		                <tr>
		                    <th scope="col" class="th-write">제목</th>
							<td><input type="text" class="input" name="title" ></td>
		                </tr>
		                 <!--  <tr>
		                    <th scope="col" class="th-write">내용</th>
							<td><textarea class="txtarea" name="content"></textarea></td>
		                </tr> -->
		                <tr>
		                    <th scope="col" class="th-write">내용</th>
							<td><textarea name="content" id="ir1" rows="10" cols="100"></textarea></td>
		                </tr>
		                <tr>
		                    <th scope="col" class="th-write">해시태그</th>
							<td>
								<div class="tagInput"></div>
								<div class="tags"></div>
								<input type="text" class="input" id="inputTag" onKeyPress="enterKey(event);" style="margin-top: 10px;">
							</td>
		                </tr>
		                <tr>
		                    <th scope="col" class="th-write" width="50%">첨부파일</th>
							<td><input type="file" class="input" name="file" ></td>
		                </tr>
	               </tbody>
	           </table>
           
			</div>
			<div class="divdtn">
		    	<button class="btn btn-dark" type="button" onclick="save();">저장</button>
		   		<button type="button" class="btn btn-dark" onclick="location.href='./board?cate=${MenuDto.code}&mode=l'">목록</button>
			</div>
		</form>
    </div>
</body>
</html>