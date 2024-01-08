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
			
			String inputPass = req.getParameter("pass");
			int idx = Integer.parseInt((String)req.getParameter("idx"));
			ChatRoomDAO dao = new ChatRoomDAO();
			pass = dao.getPass(idx);
			dao.close();
			
			if(inputPass.equals(pass)) {
				resp.getWriter().write("true");
			}else {
				resp.getWriter().write("false");
			}
			
		}else if(mode.equals("updatePlayers")) {
			
			int idx = Integer.parseInt((String)req.getParameter("idx"));
			ChatRoomDAO dao = new ChatRoomDAO();
			int total = dao.getPlayers(idx);
			dao.close();
			
			resp.getWriter().write(total+"");
			
		}
		
	}
}
