package member.info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.AuthCheck;
import common.JSFunction;
import member.memberDAO;
import member.memberDTO;

@WebServlet("/member/myinfo")
public class InfoController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!AuthCheck.checkMember(req)) {
			resp.sendRedirect("../member/login");
		}else {

			String sessionId = (String) req.getSession().getAttribute("UserId");
			
			memberDAO dao = new memberDAO();
			memberDTO member = dao.selectMemberById(sessionId);
			req.setAttribute("member", member);
			dao.close();
			
			req.getRequestDispatcher("/member/04_info/myinfo.jsp").forward(req, resp);
		}
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
		String passChange = req.getParameter("pwcng");
		
		memberDTO dto = new memberDTO();
		dto.setId(id);
		dto.setAuth_level_fk(level);
		dto.setPhone(phone);
		dto.setPassword(pass);
		
		//1. 비밀번호 확인
		memberDAO dao = new memberDAO();
		if(!dao.passwordCheck(dto)) {
			dao.close();
			JSFunction.alertLocation(resp, "비밀번호가 틀렸습니다." , "/member/myinfo");
		}else {
		
			//2. 정보 변경
			if(passChange == null) { //비밀번호 수정 안함
				
				if(!dao.updateMyInfo(dto)) {
					dao.close();
					JSFunction.alertLocation(resp, "수정실패" , "/member/myinfo");
				}
				
			}else { //비밀번호도 수정 함
				if(!dao.updateMyInfoAndPW(dto,passChange)) {
					dao.close();
					JSFunction.alertLocation(resp, "비밀번호 수정 실패" , "/member/myinfo");
				}
			}
		}
		dao.close();
		JSFunction.alertLocation(resp, "수정완료" , "/member/myinfo");
		
		
	}
}
