package member.studychat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;

import boardGame.RandomCard;

@ServerEndpoint("/ChatingServer2")
public class ChatServer extends HttpServlet{
    private static Set<Session> clients
            = Collections.synchronizedSet(new HashSet<Session>());
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	System.out.println(req.getAttribute("chatId"));
    }

    @OnOpen  // 클라이언트 접속 시 실행
    public void onOpen(Session session) {
        clients.add(session);  // 세션 추가
        System.out.println("접속자 세션 : "+session.getId());
        System.out.println("연결 발생 현재 인원 :"+clients.size());
    }

    @OnMessage  // 메시지를 받으면 실행
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("메시지 전송 : " + session.getId() + ":" + message);
        String[] messages = message.split(":");
        
        synchronized (clients) {
            for (Session client : clients) {  // 모든 클라이언트에 메시지 전달
                if (!client.equals(session)) {  // 단, 메시지를 보낸 클라이언트는 제외
                    client.getBasicRemote().sendText(message);
                }
            }
        }
    }

    @OnClose  // 클라이언트와의 연결이 끊기면 실행
    public void onClose(Session session) {
        clients.remove(session); 
        System.out.println("웹소켓 종료 : " + session.getId());
        System.out.println("연결 종료 발생 현재 인원 :"+clients.size());
    }

    @OnError  // 에러 발생 시 실행
    public void onError(Throwable e) {
        System.out.println("에러 발생");
        e.printStackTrace();
    }
}