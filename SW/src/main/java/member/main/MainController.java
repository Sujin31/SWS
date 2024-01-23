package member.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.AuthCheck;
import member.data.BoardDTO;
import member.data.NoticeDTO;
import member.info.TodoDAO;
import member.info.TodoDTO;

@WebServlet("/member/main")
public class MainController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!AuthCheck.checkMember(req)) {
			resp.sendRedirect("../member/login");
		}else {
			
			// 1. 공지
			MainBoardSection mbs = new MainBoardSection();
			List<NoticeDTO> mNoticeList = mbs.getMainMustNotices();
			List<NoticeDTO> noticeList = mbs.getMainNotices(mNoticeList.size());
			req.setAttribute("mNoticeList", mNoticeList);
			req.setAttribute("noticeList", noticeList);
			
			//2. 인기글(자유게시판)
			List<BoardDTO> topList = mbs.getTopFiveBoard();
			req.setAttribute("topList", topList);
			
			//3. 내 QnA
			String sessionId = (String) req.getSession().getAttribute("UserId");
			List<BoardDTO> myList = mbs.getMyQnA(sessionId);
			req.setAttribute("myList", myList);
			
			//To-do List
			String id = (String) req.getSession().getAttribute("UserId");
			TodoDAO todoDao = new TodoDAO();
			List<TodoDTO> todoList = todoDao.selectTodoByDate(id,LocalDate.now().toString());
			todoDao.close();
			req.setAttribute("todoList", todoList);
			
			req.getRequestDispatcher("/member/02_main/Main.jsp").forward(req, resp);
		}
		
		
		
	}
}
