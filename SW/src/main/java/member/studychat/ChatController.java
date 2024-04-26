package member.studychat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JSFunction;
import member.memberDAO;
import member.studychat.data.ChatRoomDAO;
import member.studychat.data.ChatRoomDTO;

@WebServlet("/member/chat")
public class ChatController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 방 입장
		 * */
		
		/*
		 * 방 퇴장
		 * */
		
		/*
		 * 방 폐쇄
		 * */
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardTmp = req.getParameter("boardTmp");
		String code = req.getParameter("menucode");
		String mode = req.getParameter("mode");
		String id = req.getParameter("id");
		String name = req.getParameter("title");
		String fcate = req.getParameter("searchStudent");
		String scate = req.getParameter("searchSubject");
		String pass = req.getParameter("password");
		
		if(mode.equals("create")) {
			//방 생성
			ChatRoomManager mng = new ChatRoomManager();
			ChatRoomDTO dto = mng.OpenRoom(id, name, pass, fcate, scate);
			req.setAttribute("roomInfo", dto);
			req.setAttribute("boardTmp",boardTmp);
		
			JSFunction.alertLocation(resp, "개설 완료", "./board?cate="+code+"&mode=v&idx="+dto.getId());
			
		}else if(mode.equals("checkPw")){
			//방 비밀번호 입력 매칭
			boolean result = checkPwAjax(Integer.parseInt((String)req.getParameter("idx")),req.getParameter("pass"));
			resp.getWriter().write(String.valueOf(result));
			
		}else if(mode.equals("curPlayers")) {
			//view에서 참여자 수
			int result = curPlayers( Integer.parseInt((String)req.getParameter("idx")));
			resp.getWriter().write(result+"");
			
		}
		
	}
	
	
	private boolean checkPwAjax(int idx, String input) {
		ChatRoomDAO dao = new ChatRoomDAO();
		boolean result = dao.getPass(idx,input);
		dao.close();
		
		return result;
	}
	
	private int curPlayers(int idx) {
		ChatRoomDAO dao = new ChatRoomDAO();
		int result = dao.getPlayers(idx);
		dao.close();
		
		return result;
	}
}
