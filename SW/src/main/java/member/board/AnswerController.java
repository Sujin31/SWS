package member.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.JSFunction;
import common.LoggingDB;
import manage.MenuDAO;
import manage.MenuDTO;
import member.data.AnswerDAO;
import member.data.AnswerDTO;

@WebServlet("/member/answer")
public class AnswerController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("UserId");
		String code = req.getParameter("cate");
		String mode = req.getParameter("mode");
		int board_idx = Integer.parseInt(req.getParameter("boardidx"));
		int idx = Integer.parseInt(req.getParameter("idx"));
		int result = 0;
		
		if(mode.equals("delete")) {
			AnswerDAO dao = new AnswerDAO();
			result = dao.deleteAnswerAndComment(id,idx);
			dao.close();
			
			//log
			LoggingDB logDB = new LoggingDB();
			String methodName = "deleteAnswerAndComment";
			
			if(result >= 1) {
				logDB.log(session, methodName, "success_"+idx);
				JSFunction.alertLocation(resp, "삭제 완료", "./board?cate="+code+"&mode=v&idx="+board_idx);
			}else {
				logDB.log(session, methodName, "fail_"+idx);
				JSFunction.alertLocation(resp, "삭제 오류", "./board?cate="+code+"&mode=v&idx="+board_idx);
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//String boardTmp = req.getParameter("boardTmp");
		String code = req.getParameter("menucode");
		String mode = req.getParameter("mode");
		String id = req.getParameter("id");
		int board_idx = Integer.parseInt(req.getParameter("boardidx"));
		String answer = req.getParameter("answer");
		int result = 0;
		
		//log
		LoggingDB logDB = new LoggingDB();
		String methodName = "";
				
		if(mode.equals("write")) {
			AnswerDTO dto = new AnswerDTO();
			dto.setBoard_fk(board_idx);
			dto.setId(id);
			dto.setAnswer(answer);
			
			AnswerDAO dao = new AnswerDAO();
			result = dao.insertAnswer(dto);
			dao.close();
			
			methodName = "insertAnswer";
			
			if(result == 1) {
				logDB.log(req.getSession(), methodName, "seccess");
				JSFunction.alertLocation(resp, "작성 완료", "./board?cate="+code+"&mode=v&idx="+board_idx);
			}else {
				logDB.log(req.getSession(), methodName, "fail");
				JSFunction.alertLocation(resp, "작성 오류", "./board?cate="+code+"&mode=v&idx="+board_idx);
			}
		}else if(mode.equals("edit")) {
			
			int idx = Integer.parseInt(req.getParameter("idx"));
			
			AnswerDTO dto = new AnswerDTO();
			dto.setIdx(idx);
			dto.setAnswer(answer);
			
			AnswerDAO dao = new AnswerDAO();
			result = dao.editAnswer(dto);
			dao.close();
			
			methodName = "editAnswer";
			
			if(result == 1) {
				logDB.log(req.getSession(), methodName, "seccess_"+idx);
				JSFunction.alertLocation(resp, "수정 완료", "./board?cate="+code+"&mode=v&idx="+board_idx);
			}else {
				logDB.log(req.getSession(), methodName, "fail_"+idx);
				JSFunction.alertLocation(resp, "수정 오류", "./board?cate="+code+"&mode=v&idx="+board_idx);
			}
		}
		
	}
}
