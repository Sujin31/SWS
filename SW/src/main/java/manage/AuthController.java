package manage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JSFunction;
import common.AuthCheck;
import member.AuthDAO;
import member.AuthLevelDTO;

@WebServlet("/manage/makeauth")
public class AuthController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!AuthCheck.checkManager(req)) {
			resp.sendRedirect("../manage/login");
		}else {
			
			AuthDAO dao = new AuthDAO();
			
			if(req.getParameter("del") != null) {
				String idx = req.getParameter("idx");
				int result = dao.deleteAuthLevel(idx);
				if(result == 1) {
					resp.sendRedirect("./makeauth");
				}else {
					JSFunction.alertLocation(resp, "삭제 실패", "./makeauth");
				}
				
			}else {
				List<AuthLevelDTO> list = dao.getAuthLevelsAll();
				dao.close();
				req.setAttribute("dto", list);
				
				req.getRequestDispatcher("/manager/menu/MakeAuth.jsp").forward(req, resp);
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		AuthLevelDTO dto = new AuthLevelDTO();
		dto.setIdx(req.getParameter("idx"));
		dto.setIsmng(req.getParameter("ismng"));
		dto.setName(req.getParameter("name"));
		
		AuthDAO dao = new AuthDAO();
		int result = dao.setAuthLevel(dto);
		dao.close();
		if(result == 1) {
			resp.sendRedirect("./makeauth");
		}else {
			JSFunction.alertBack(resp, "추가 실패");
		}
	}
}
