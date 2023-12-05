package manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.JSFunction;
import common.MemberCheck;
import member.memberDAO;

@WebServlet("/manage/makemenu")
public class MakeMenuController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!MemberCheck.checkManager(req)) {
			resp.sendRedirect("../manage/login");
		}else {
			
			MenuDAO dao = new MenuDAO();
			ArrayList<String> toplist = new ArrayList<>();
			toplist = dao.SelectTopMenu();
			JSONArray menulist = dao.SelectMenuList(toplist);
			dao.close();
			
			req.setAttribute("menulist", menulist);
			req.getRequestDispatcher("/manager/menu/MakeMenu.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String code = req.getParameter("code");
		String pmenu_code = req.getParameter("pmenu_code");
		String depts = req.getParameter("depts");
		String link = req.getParameter("link");
		String tmp = req.getParameter("tmp");
		String order = req.getParameter("order");
		
		MenuDTO dto = new MenuDTO();
		dto.setName(name);
		dto.setCode(code);
		dto.setPmenu_code(pmenu_code);
		dto.setDepts(Integer.parseInt(depts));
		dto.setLink(link);
		dto.setBoard_tmp(tmp);
		dto.setList_order(Integer.parseInt(order));
		
		MenuDAO dao = new MenuDAO();		
		int result = dao.CrateMenu(dto);
		dao.close();
		
		
		if(result == 1) {
			JSFunction.alertLocation(resp, "추가완료", "./makemenu");
		}else {
			JSFunction.alertLocation(resp, "오류", "./makemenu");
		}
	}
}
