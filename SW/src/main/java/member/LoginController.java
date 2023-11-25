package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/login.do")
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/member/01_login/Login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		memberDAO dao = new memberDAO();
		memberDTO dto = dao.memberLogin(id, pw);
		dao.close();
		
		if( dto.getId() != null) {
			System.out.println(id+"님 로그인");
			
			HttpSession session = req.getSession();
			session.setAttribute("UserId", id);
			resp.sendRedirect("../member/02_main/Main.jsp");
			//resp.sendRedirect("../member/main.do");
		}else {
			System.out.println("정보없음");
			req.setAttribute("errorMsg", "아이디 또는 비밀번호를 잘못 입력했습니다.");
			req.getRequestDispatcher("/member/01_login/Login.jsp").forward(req, resp);
		}
	}
}
