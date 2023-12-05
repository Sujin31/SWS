package member.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MemberCheck;

@WebServlet("/member/main")
public class MainController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!MemberCheck.checkMember(req)) {
			resp.sendRedirect("../member/login");
		}else {
			req.getRequestDispatcher("/member/02_main/Main.jsp").forward(req, resp);
		}
		
	}
}
