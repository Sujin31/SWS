package manage.login;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JSFunction;
import common.MemberCheck;
import member.AuthDAO;
import member.AuthLevelDTO;
import member.memberDAO;
import member.memberDTO;

@WebServlet("/manage/makemanager")
public class MakeManagerController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!MemberCheck.checkManager(req)) {
			resp.sendRedirect("../manage/login");
		}else {
			if(req.getParameter("del") != null && req.getParameter("id") != "admin") {
				String id = req.getParameter("id");
				ManagerDAO dao = new ManagerDAO();
				int result = dao.delManager(id);
				dao.close();
				memberDAO dao2= new memberDAO();
				int result2 = dao2.delMember(id);
				dao.close();
				
				if(result == 1 && result2 == 1) {
					resp.sendRedirect("../manage/makemanager");
				}else {
					JSFunction.alertBack(resp, "오류");
				}
				
				
			}else {
				ManagerDAO dao = new ManagerDAO();
				List<ManagerDTO> list = dao.getManagerList();
				req.setAttribute("list", list);
				dao.close();
				
				AuthDAO dao2 = new AuthDAO();
				List<AuthLevelDTO> auth = dao2.getAuthLevelsOfManager();
				req.setAttribute("auth", auth);
				dao2.close();
				
				req.getRequestDispatcher("/manager/login/mekeManager.jsp").forward(req, resp);
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String pw = req.getParameter("password");
		String auth = req.getParameter("auth");
		String name = req.getParameter("name");
		String birth = req.getParameter("birth");
		String gender = req.getParameter("gender");
		String phone = req.getParameter("phone");
		
		ManagerDAO dao = new ManagerDAO();
		ManagerDTO dto = new ManagerDTO();
		dto.setId(id);
		dto.setAuth_level_fk(auth);
		dto.setName(name);
		dto.setPassword(pw);
		dto.setPhone(phone);
		int result = dao.setManager(dto);
		
		memberDAO dao2 = new memberDAO();
		memberDTO dto2 = new memberDTO();
		dto2.setId(id);
		dto2.setAuth_level_fk(auth);
		dto2.setName(name+"[MG]");
		dto2.setBirth(birth);
		dto2.setGender(gender);
		dto2.setPassword(pw);
		dto2.setPhone(phone);
		int result2 = dao2.signUp(dto2);
		
		if(result == 1 && result2 == 1) {
			resp.sendRedirect("../manage/makemanager");
		}else {
			JSFunction.alertBack(resp, "오류");
		}
	}
}
