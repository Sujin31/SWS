package member.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MemberCheck;
@WebServlet("/member/board")
public class BoardController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!MemberCheck.checkMember(req)) {
			resp.sendRedirect("../member/login.do");
		}else {
			req.getRequestDispatcher("/member/03_board/Board.jsp").forward(req, resp);
		}
	}
}
