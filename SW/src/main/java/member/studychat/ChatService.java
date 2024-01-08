package member.studychat;


import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import boardGame.HttpSessionConfigurator;
import member.studychat.data.ChatRoomDAO;

@ServerEndpoint(value="/ChatingServer",configurator = HttpSessionConfigurator.class)
public class ChatService{
	
	private static Set<Session> clients	= Collections.synchronizedSet(new HashSet<Session>());
	private Map<Session, EndpointConfig> configs = Collections.synchronizedMap(new HashMap<>());
	
	private static Map<Session,Integer> clientMap = Collections.synchronizedMap(new HashMap<Session,Integer>());
	
	@OnOpen  // 클라이언트 접속 시 실행
    public void onOpen(Session session, EndpointConfig config) {
        clients.add(session);  // 세션 추가
        System.out.println("접속자 세션 : "+session.getId());
        //System.out.println("연결 발생 현재 인원 :"+clients.size());
        
        //HTTPSESSION
        if (!configs.containsKey(session)) {      // session 클래스는 connection이 될 때마다 인스턴스 생성되는 값이기 때문에 키로서 사용할 수 있다.      
        	configs.put(session, config);    
        }
        HttpSession hsession = (HttpSession) config.getUserProperties().get(HttpSessionConfigurator.Session);
        int roomIdx = Integer.parseInt((String)hsession.getAttribute("roomIdx"));
        clientMap.put(session, roomIdx);
        
        //입장 count+
        ChatRoomDAO dao = new ChatRoomDAO();
        dao.PlayerCountUp(roomIdx);
        dao.close();
        
    }

    @OnMessage  // 메시지를 받으면 실행
    public void onMessage(String message, Session session) throws IOException {
        //System.out.println("메시지 전송 : " + session.getId() + ":" + message);
        boolean flag = true;
        String[] messages = message.split("\\|");
        
        if(messages[0].equals("/enter")) {
        	message = messages[1]+"님이 입장하였습니다.";
        	flag = false;
        }else if(messages[0].equals("/exit")){
        	message = messages[1]+"님이 퇴장하였습니다.";
        	flag = false;
        }else {
        	//System.out.println("메시지 전송 : " + session.getId() + ":" + message);
	        ChatRoomDAO dao = new ChatRoomDAO();
	        dao.insertUserChat(clientMap.get(session), messages[0], messages[1]);
	        dao.close();
        }
        
        synchronized (clients) {
            for (Session client : clients) {  // 모든 클라이언트
            	if(clientMap.get(client) == clientMap.get(session)) {	//같은 방 클라이언트만 전달
	                if (!client.equals(session) || !flag) {  // 단, 메시지를 보낸 클라이언트는 제외
	                    client.getBasicRemote().sendText(message);
	                }
            	}
            }
        }
        
    }

    @OnClose  // 클라이언트와의 연결이 끊기면 실행
    public void onClose(Session session) {
    	
    	//퇴장 count-
    	
        int roomIdx = clientMap.get(session);
        ChatRoomDAO dao = new ChatRoomDAO();
        dao.PlayerCountDown(roomIdx);
        dao.DeleteZeroPlayer(roomIdx);
        dao.close();
    	
        
        clients.remove(session); 
        clientMap.remove(session);
        System.out.println("웹소켓 종료 : " + session.getId());
        //System.out.println("연결 종료 발생 현재 인원 :"+clients.size());
    }

    @OnError  // 에러 발생 시 실행
    public void onError(Throwable e) {
        System.out.println("에러 발생");
        e.printStackTrace();
    }
}
