package member.board;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.AuthCheck;
import manage.MenuAuthDTO;
import member.info.TodoDAO;
import member.info.TodoDTO;
@WebServlet("/member/board")
public class BoardController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!AuthCheck.checkMember(req)) {
			resp.sendRedirect("../member/login");
		}else {
			MenuAuthDTO dto = AuthCheck.loadAuthMember(req);
			req.setAttribute("authdto", dto);
			
			//To-do List
			String id = (String) req.getSession().getAttribute("UserId");
			TodoDAO todoDao = new TodoDAO();
			List<TodoDTO> todoList = todoDao.selectTodoByDate(id,LocalDate.now().toString());
			todoDao.close();
			req.setAttribute("todoList", todoList);
			
			req.getRequestDispatcher("/member/03_board/Board.jsp").forward(req, resp);
		}
	}
}
