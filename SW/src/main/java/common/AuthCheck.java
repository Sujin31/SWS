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
		MenuAuthDTO auth = (MenuAuthDTO) req.getAttribute("authdto");
		String mode = req.getParameter("mode");
		
		
		if(sessionId != null) {
			result = true;
		}
		
		//get 파라미터로 이동 방지
		if(auth != null) {
			if(mode.equals("r") && auth.getMread().equals("N")) result = false;
			if(mode.equals("w") && auth.getMwrite().equals("N")) result = false;
			if(mode.equals("e") && auth.getMwrite().equals("N")) result = false;
			if(mode.equals("d") && auth.getMwrite().equals("N")) result = false;
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
	
	//메뉴 권한 가져오기
	public static MenuAuthDTO loadAuthMember(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String auth = (String)session.getAttribute("Auth");
		String menu = req.getParameter("cate");
		
		MenuAuthDAO dao = new MenuAuthDAO();
		MenuAuthDTO dto = dao.getMenuAuthBoard(auth, menu);
		dao.close();
		
		return dto;
	}
	
	//메뉴 권한과 모드 검사
	public static boolean checkAuthMode(HttpServletRequest req) {
		boolean result = true;
		
		HttpSession session = req.getSession();
		
		
		System.out.println(req.getAttribute("authdto"));
		return result;
	}
}
