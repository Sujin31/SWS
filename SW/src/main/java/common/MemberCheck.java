package common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MemberCheck {
	public static boolean checkMember(HttpServletRequest req) {
		boolean result = false;
		
		HttpSession session = req.getSession();
		String sessionId = (String) session.getAttribute("UserId");

		if(sessionId != null) {
			result = true;
		}
		return result;
	}
}
