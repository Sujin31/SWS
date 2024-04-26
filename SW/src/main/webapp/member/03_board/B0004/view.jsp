<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<% pageContext.setAttribute("CRLF", "\r\n"); %>
<% pageContext.setAttribute("LF", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script>
var webSocket
    = new WebSocket("<%= application.getInitParameter("CHAT_ADDR") %>/ChatingServer");
var chatWindow, chatMessage, chatId;

// 채팅창이 열리면 대화창, 메시지 입력창, 대화명 표시란으로 사용할 DOM 객체 저장
window.onload = function() {
    chatWindow = document.getElementById("chatWindow");
    chatMessage = document.getElementById("chatMessage");
    chatId = "${UserId}"
    //chatRoomNum = "${ param.chatRoomNum }";
}

// 메시지 전송
function sendMessage() {
    // 대화창에 표시
    chatWindow.innerHTML += "<div class='myMsg'>" + chatMessage.value + "</div>"
    webSocket.send(chatId + '|' + chatMessage.value);  // 서버로 전송
    chatMessage.value = "";  // 메시지 입력창 내용 지우기
    chatWindow.scrollTop = chatWindow.scrollHeight;  // 대화창 스크롤
}

// 서버와의 연결 종료
function disconnect() {
	webSocket.send("/exit|"+chatId);
    webSocket.close();
}

// 엔터 키 입력 처리
function enterKey() {
    if (window.event.keyCode == 13) {  // 13은 'Enter' 키의 코드값
        sendMessage();
    }
}

// 웹소켓 서버에 연결됐을 때 실행
webSocket.onopen = function(event) {   
	webSocket.send("/enter|"+chatId);
    chatWindow.innerHTML += "<span style='color:grey;'><<채팅 에티켓을 준수해주세요.>></span><br/>";
};

// 웹소켓이 닫혔을 때(서버와의 연결이 끊겼을 때) 실행
webSocket.onclose = function(event) {
    //chatWindow.innerHTML += "웹소켓 서버가 종료되었습니다.<br/>";
    location.href="/member/board?cate=menu027&mode=l";
};

// 에러 발생 시 실행
webSocket.onerror = function(event) { 
    alert(event.data);
    chatWindow.innerHTML += "채팅 중 에러가 발생하였습니다.<br/>";
}; 

// 메시지를 받았을 때 실행
webSocket.onmessage = function(event) { 
	updatePlayers();
    var message = event.data.split("|");  // 대화명과 메시지 분리
    var sender = message[0];   // 보낸 사람의 대화명
    var content = message[1];  // 메시지 내용
    if (content != "" && typeof content != "undefined") {
        if (content.match("/")) {  // 귓속말
            if (content.match(("/" + chatId))) {  // 나에게 보낸 메시지만 출력
                var temp = content.replace(("/" + chatId), "[귓속말] : ");
                chatWindow.innerHTML += "<div>" + sender + "" + temp + "</div>";
            }
        }
        else {  // 일반 대화
            chatWindow.innerHTML += "<div>" + sender + " : " + content + "</div>";
        }
    }else{
    	chatWindow.innerHTML += "<div>" + sender + "</div>";
    }
   	chatWindow.scrollTop = chatWindow.scrollHeight; 
};

function curPlayers(){
	var idx = ${param.idx};
	var total = "";
	$.ajax({
		url : "./chat",
		type : "post",
		async: false, 
		data : {"mode" : "curPlayers", "idx" : idx },
		dataType : "text",
		success : function(result){
			
			total=result;
		}
	});
	console.log(total);
	document.getElementById("curPlayer").innerText=total;
}
</script>
<style type="text/css">
.myMsg{text-align:right;}
#chatWindow{
	overflow-y:scroll;
	width:90%;
	height:500px;
	margin: auto;
}
</style>
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

    <div id="board-list">
        <div class="container">
            <table class="board-table">
				<tr>
					<td id="title">
						[${dto.fcate }/${dto.scate }]  ${fn:replace(fn:replace(fn:escapeXml(dto.name), CRLF, '<br/>'), LF, '<br/>')}
						<span style="float: right; font-size: 15px">참여자 : <span id="curPlayer"> ${dto.participant } </span>명</span>
					</td>
				</tr>
            </table>
            
		    
		    <div id="chatWindow"></div>
		    <table>
		    	<tr>
		    		<td>
		    			<textarea rows="10" cols="150" id="chatMessage" onkeyup="enterKey();"></textarea>
		    		</td>
		    		
		    		<td style="padding-left:10px">
		    			<button class="btn btn-dark btn-enter" onclick="sendMessage();">전송</button>
		    		</td>
		    		<td style="padding-left:10px">
		    			<button class="btn btn-dark btn-enter" onclick="disconnect();">나가기</button>
		    		</td>
		    	</tr>
		    </table>
		    
		    
            <div class="divdtn" style="display: none;">
            	<button type="button" class="btn btn-dark " onclick="location.href='./board?cate=${MenuDto.code}&mode=l'">목록</button>
            </div>
        </div>
    </div>
</body>
</html>