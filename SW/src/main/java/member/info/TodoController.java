package member.info;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.AuthCheck;
import common.JSFunction;
import common.LoggingDB;

@WebServlet("/member/todo")
public class TodoController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!AuthCheck.checkMember(req)) {
			resp.sendRedirect("../member/login");
		}else {
			String id = (String) req.getSession().getAttribute("UserId");
			String selDate = req.getParameter("selDate");
			String mode = req.getParameter("mode");
			
			if(selDate == null) {
				selDate = LocalDate.now().toString();
			}
			
			//log
			LoggingDB logDB = new LoggingDB();
			String methodName = "";
			
			
			/*get - 완료 or 삭제*/
			if(mode != null ) {
				if(mode.equals("done")) { //완료
					int idx = Integer.parseInt(req.getParameter("idx"));
					String done = req.getParameter("done");
					TodoDAO dao = new TodoDAO();
					int result = dao.updateDoneTodo(idx, done);
					dao.close();
					
					methodName = "updateDoneTodo";
	
					if(result == 1) {
						logDB.log(req.getSession(), methodName, "success_"+idx);
						JSFunction.Location(resp, "/member/todo?selDate="+selDate);
					}else {
						logDB.log(req.getSession(), methodName, "fail_"+idx);
						JSFunction.alertLocation(resp, "요청 실패", "/member/todo?selDate="+selDate);
					}
					
				}else if(mode.equals("del")) {//삭제
					int idx = Integer.parseInt(req.getParameter("idx"));
					TodoDAO dao = new TodoDAO();
					int result = dao.deleteTodo(idx, id);
					dao.close();
					
					methodName = "deleteTodo";
					
					if(result == 1) {
						logDB.log(req.getSession(), methodName, "success_"+idx);
						JSFunction.Location(resp, "/member/todo?selDate="+selDate);
					}else {
						logDB.log(req.getSession(), methodName, "fail_"+idx);
						JSFunction.alertLocation(resp, "요청 실패", "/member/todo?selDate="+selDate);
					}
				}
			}else {
			/**/
				
				
				//이전 날짜, 이후 날짜
				LocalDate stand = LocalDate.parse(selDate, DateTimeFormatter.ISO_DATE);
				String tomorrow = stand.plusDays(1).toString();
				String yester = stand.minusDays(1).toString();
				
				TodoDAO dao = new TodoDAO();
				List<TodoDTO> list = dao.selectTodoByDate(id, selDate);
				dao.close();
				
				req.setAttribute("selDate", selDate);
				req.setAttribute("tomorrow", tomorrow);
				req.setAttribute("yester", yester);
				req.setAttribute("list", list);
				req.getRequestDispatcher("/member/04_info/todo.jsp").forward(req, resp);
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = (String) req.getSession().getAttribute("UserId");
		String todo_date = req.getParameter("date");
		String content = req.getParameter("content");
		String mode = req.getParameter("mode");
		int result = 0;

		if(todo_date == null) {
			todo_date = LocalDate.now().toString();
		}
		
		//log
		LoggingDB logDB = new LoggingDB();
		String methodName = "";
		
		if(mode.equals("save")) {
			TodoDTO dto = new TodoDTO();
			dto.setId(id);
			dto.setTodo_date(todo_date);
			dto.setContent(content);
			
			TodoDAO dao = new TodoDAO();
			result = dao.insertTodo(dto);
			dao.close();
			
			methodName = "insertTodo";
			
		}else {
			int idx = Integer.parseInt(req.getParameter("idx"));
			TodoDAO dao = new TodoDAO();
			result = dao.updateContentTodo(idx, content);
			dao.close();
			
			methodName = "updateContentTodo";
		}
		
		if(result == 1 ) {
			logDB.log(req.getSession(), methodName, "success");
			JSFunction.Location(resp,"/member/todo?selDate="+todo_date);
		}else {
			logDB.log(req.getSession(), methodName, "fail");
			JSFunction.alertLocation(resp, "요청 실패", "/member/todo?selDate="+todo_date);
		}
	}
}
