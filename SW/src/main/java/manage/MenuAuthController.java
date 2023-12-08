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

import common.AuthCheck;

@WebServlet("/manage/menuauth")
public class MenuAuthController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!AuthCheck.checkManager(req)) {
			resp.sendRedirect("../manage/login");
		}else {
			
			MenuDAO dao = new MenuDAO();
			ArrayList<String> toplist = dao.SelectTopMenu();
			JSONArray arr = dao.getMenuListforLast(toplist);
			dao.close();
			req.setAttribute("dto", arr);
			req.getRequestDispatcher("/manager/menu/MakeMenuAuth.jsp").forward(req, resp);
			
		}
	}
	
}
