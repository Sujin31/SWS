package boardGame;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/boardGame")
public class BoardGameController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String chatId = req.getParameter("chatId");
		req.getSession().setAttribute("chatId", chatId);
		
		req.getRequestDispatcher("/boardGame/ChatWindow_game.jsp").forward(req, resp);
	}
}
