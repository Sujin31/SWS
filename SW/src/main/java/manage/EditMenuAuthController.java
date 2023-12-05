package manage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JSFunction;
import common.MemberCheck;
import member.AuthDAO;
import member.AuthLevelDTO;

@WebServlet("/manage/editMenuAuth")
public class EditMenuAuthController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!MemberCheck.checkManager(req)) {
			resp.sendRedirect("../manage/login");
		}else {
			
			String code = req.getParameter("code");
			
			MenuAuthDAO Mdao = new MenuAuthDAO();
			List<MenuAuthDTO> list = Mdao.getMenuAuthList(code);
			Mdao.close();
			req.setAttribute("list", list);
			
			req.getRequestDispatcher("/manager/menu/p_MakeMenuAuth.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*모든 req값 받기*/
//		Enumeration params = req.getParameterNames();
//		while(params.hasMoreElements()) {
//		  String name = (String) params.nextElement();
//		  System.out.print(name + " : " + req.getParameter(name) + "     "); 
//		}
//		System.out.println();
		
		
		
		int length = Integer.parseInt(req.getParameter("length"));
		List<MenuAuthDTO> list = new Vector<MenuAuthDTO>();
		String menu = req.getParameter("menu_fk");
		for(int i =1; i<=length ; i++) {
			MenuAuthDTO dto = new MenuAuthDTO();
			dto.setAuth_level_fk(req.getParameter("auth"+i));
			dto.setMenu_fk(menu);
			dto.setMread(req.getParameter("read"+i));
			dto.setMwrite(req.getParameter("write"+i));
			dto.setMcomment(req.getParameter("comment"+i));
			dto.setReply(req.getParameter("reply"+i));
			
			list.add(dto);
		}
		MenuAuthDAO dao = new MenuAuthDAO();
		int result = dao.setMenuAuthList(list,menu);
		if(result == 1) {
			//resp.sendRedirect("./editMenuAuth");
			JSFunction.alertBack(resp, "ok");
		}

	}
}
