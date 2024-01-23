package member.info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.JSFunction;
import member.memberDAO;
import member.memberDTO;

@WebServlet("/member/myinfo")
public class InfoController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String sessionId = (String) req.getSession().getAttribute("UserId");
		
		memberDAO dao = new memberDAO();
		memberDTO member = dao.selectMemberById(sessionId);
		req.setAttribute("member", member);
		dao.close();
		
		req.getRequestDispatcher("/member/04_info/myinfo.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 저장
		 * 등급,전화번호,비밀번호 변경 가능
		 * */
		String id = req.getParameter("id");
		String level = req.getParameter("level");
		String phone = req.getParameter("phone");
		String pass = req.getParameter("pw");
		
		memberDTO dto = new memberDTO();
		dto.setId(id);
		dto.setAuth_level_fk(level);
		dto.setPhone(phone);
		dto.setPassword(pass);
		
		memberDAO dao = new memberDAO();
		int result = dao.updateMyInfo(dto);
		dao.close();
		
		if(result == 1) {
			JSFunction.alertLocation(resp, "수정완료" , "/member/myinfo");
		}else {
			JSFunction.alertLocation(resp, "수정실패" , "/member/myinfo");
		}
		
	}
}
