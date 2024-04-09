package member.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JSFunction;
import common.LoggingDB;
import member.data.CommentDAO;
import member.data.CommentDTO;

@WebServlet("/member/comment")
public class CommentController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int idx = Integer.parseInt(req.getParameter("idx"));
		String mode = req.getParameter("mode");
		String menu = req.getParameter("cate");
		int boardIdx = Integer.parseInt(req.getParameter("bidx")) ;
		int result =0 ;
		
		//log
		LoggingDB logDB = new LoggingDB();
		
		if(mode.equals("delete")) {
			CommentDAO dao = new CommentDAO();
			result = dao.deleteComment(idx);
			if(result == 1) {
				logDB.log(req.getSession(), "deleteComment", "success_"+idx);
				JSFunction.alertLocation(resp, "삭제 완료", "../member/board?cate="+menu+"&mode=v&idx="+boardIdx);
			}else {
				logDB.log(req.getSession(), "deleteComment", "fail_"+idx);
				JSFunction.alertBack(resp, "댓글 삭제 오류");
			}
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mode = req.getParameter("mode");
		String id = req.getParameter("id");
		int boardIdx = Integer.parseInt(req.getParameter("board_idx")) ;
		String menu = req.getParameter("menu");
		String comment = req.getParameter("comment");
		String isanswer = req.getParameter("isanswer");
		/*답변 게시글 댓글은 boardIdx가 answerIdx여야 함*/
		String answerIdx = req.getParameter("answer_idx");
		String idx = req.getParameter("idx");
		
		int result = 0;
		
		//log
		LoggingDB logDB = new LoggingDB();
		String methodName = "";
		
		CommentDTO dto = new CommentDTO();
		dto.setId(id);
		dto.setComments(comment);
		dto.setIsanswer(isanswer);
		
		
		
		if(answerIdx != null) {
			dto.setBoard_fk(Integer.parseInt(answerIdx));
		}else {
			dto.setBoard_fk(boardIdx);
		}
		
		CommentDAO dao = new CommentDAO();
		
		if(mode.equals("write")) {
			idx = "";
			result = dao.insertComment(dto);
			methodName = "insertComment";
			
		}else if(mode.equals("add")) {
			
			dto.setIscomment(Integer.parseInt(idx));
			result = dao.insertAddComment(dto);
			methodName = "insertAddComment";
			
		}else if(mode.equals("edit")) {
			
			dto.setIdx(Integer.parseInt(idx));
			result = dao.editComment(dto);
			methodName = "editComment";
			
		}
			
		dao.close();
		
		if(result == 1) {		
			logDB.log(req.getSession(), methodName, "success_"+idx);
			resp.sendRedirect("../member/board?cate="+menu+"&mode=v&idx="+boardIdx);
		}else {
			logDB.log(req.getSession(), methodName, "fail");
			JSFunction.alertBack(resp, "오류");
		}
	}
}
