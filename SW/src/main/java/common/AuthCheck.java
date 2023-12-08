package common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import manage.MenuAuthDAO;
import manage.MenuAuthDTO;

public class AuthCheck {
	public static boolean checkMember(HttpServletRequest req) {
		boolean result = false;
		
		HttpSession session = req.getSession();
		String sessionId = (String) session.getAttribute("UserId");

		if(sessionId != null) {
			result = true;
		}
		return result;
	}
	
	public static boolean checkManager(HttpServletRequest req) {
		boolean result = false;
		
		HttpSession session = req.getSession();
		String sessionId = (String) session.getAttribute("ManagerId");

		if(sessionId != null) {
			result = true;
		}
		return result;
	}
	
	public static MenuAuthDTO checkAuthMember(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String auth = (String)session.getAttribute("Auth");
		String menu = req.getParameter("cate");
		
		MenuAuthDAO dao = new MenuAuthDAO();
		MenuAuthDTO dto = dao.getMenuAuthBoard(auth, menu);
		dao.close();
		
		return dto;
	}
}
