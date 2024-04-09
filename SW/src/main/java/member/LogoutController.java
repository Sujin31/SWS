package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.LoggingDB;
@WebServlet("/member/logout")
public class LogoutController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		//log
		LoggingDB logDB = new LoggingDB();
		logDB.log(session,"logout","success");
		
		session.removeAttribute("UserId");
		
		resp.sendRedirect("../member/login");
	}
}
