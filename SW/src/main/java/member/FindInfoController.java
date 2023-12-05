package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JSFunction;

@WebServlet("/member/findInfo")
public class FindInfoController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode"); 
		if(mode.equals("id"))
			req.getRequestDispatcher("/member/01_login/FindId.jsp").forward(req, resp);
		else req.getRequestDispatcher("/member/01_login/FindPw.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mode = req.getParameter("mode");
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		
		memberDAO dao = new memberDAO();
		if(mode.equals("id")) { //id 찾기
			
			id = dao.findId(name,phone);
			if(id.equals("")) {
				req.setAttribute("errorMsg", "정보가 일치하지 않습니다.");
				req.getRequestDispatcher("/member/01_login/FindId.jsp").forward(req, resp);
			}else {
				req.setAttribute("id", id);
				req.getRequestDispatcher("/member/01_login/FoundId.jsp").forward(req, resp);
			}
			
		}else if(mode.equals("pw")){ //pw 찾기
			String check_id = dao.findPw(id, name, phone);
			if(check_id.equals("")) {//정보일치안함
				req.setAttribute("errorMsg", "정보가 일치하지 않습니다.");
				req.getRequestDispatcher("/member/01_login/FindPw.jsp").forward(req, resp);
			}else {
				req.setAttribute("id", check_id);
				req.getRequestDispatcher("/member/01_login/FoundPw.jsp").forward(req, resp);
			}
		}else if(mode.equals("resetPw")) { //pw재설정
			String pw = req.getParameter("pw");
			int confirm = dao.resetPw(id, pw);
			if( confirm == 1) {
				JSFunction.alertLocation(resp, "비밀번호 재설정이 완료되었습니다.", "./login.do");
			}
		}
			
	}
}
