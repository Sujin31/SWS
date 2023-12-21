package boardGame;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;

import boardGame.RandomCard;

@ServerEndpoint(value ="/GameChatingServer", configurator = HttpSessionConfigurator.class)
public class ChatServer {
    private static Set<Session> clients
            = Collections.synchronizedSet(new HashSet<Session>());
    private Map<Session, EndpointConfig> configs = Collections.synchronizedMap(new HashMap<>());
    

    @OnOpen  // 클라이언트 접속 시 실행
    public void onOpen(Session session, EndpointConfig config) {
        clients.add(session);  // 세션 추가
        System.out.println("접속자 세션 : "+session.getId());
        System.out.println("연결 발생 현재 인원 :"+clients.size());
        
        if (!configs.containsKey(session)) {      // session 클래스는 connection이 될 때마다 인스턴스 생성되는 값이기 때문에 키로서 사용할 수 있다.      
        	configs.put(session, config);    
        }
        
        HttpSession hsession = (HttpSession) config.getUserProperties().get(HttpSessionConfigurator.Session);
        String id = (String)hsession.getAttribute("chatId");
        System.out.println(id);
    }

    @OnMessage  // 메시지를 받으면 실행
    public void onMessage(String message, Session session) throws IOException {
    	String id ="";
        System.out.println("메시지 전송 : " + message);
        
        //httpSession 가져오기
        if (configs.containsKey(session)) {
        	
        	EndpointConfig config = configs.get(session);
	        if (!configs.containsKey(session)) {    
	        	configs.put(session, config);
	    	}
	        HttpSession hsession = (HttpSession) config.getUserProperties().get(HttpSessionConfigurator.Session);
	        id = (String)hsession.getAttribute("chatId");
	        
        }
        System.out.println(id);
        System.out.println(message);
        //명령어 실행 (admin만 가능)
        if(id.equals("admin")) {
        	System.out.println("명령어 실행");
	        JSONObject jObj = RandomCard.command(message, clients.size());
	        System.out.println(jObj);
	        synchronized (clients) {
	        	
	        }
        }
//        synchronized (clients) {
//            for (Session client : clients) {  // 모든 클라이언트에 메시지 전달
//                if (!client.equals(session)) {  // 단, 메시지를 보낸 클라이언트는 제외
//                    client.getBasicRemote().sendText(message);
//                }
//            }
//        }
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