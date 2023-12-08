package manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.AuthCheck;

@WebServlet("/manage/main")
public class MainController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!AuthCheck.checkManager(req)) {
			resp.sendRedirect("../manage/login");
		}else {
			req.getRequestDispatcher("/manager/Main.jsp").forward(req, resp);
		}
		
	}
}
