package manage.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/manage/login")
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/manager/login/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		ManagerDAO dao = new ManagerDAO();
		ManagerDTO dto = dao.managerLogin(id, pw);
		dao.close();
		
		if( dto.getId() != null) {
			HttpSession session = req.getSession();
			session.setAttribute("ManagerId", id);
			session.setAttribute("Auth", dto.getAuth_level_fk());
			resp.sendRedirect("../manage/main");
		}else {
			System.out.println("관리자 정보없음");
			req.setAttribute("errorMsg", "아이디 또는 비밀번호를 잘못 입력했습니다.");
			req.getRequestDispatcher("/manager/login/login.jsp").forward(req, resp);
		}
	}
}
