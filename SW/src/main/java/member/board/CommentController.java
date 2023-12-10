package member.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JSFunction;
import member.data.CommentDAO;
import member.data.CommentDTO;

@WebServlet("/member/comment")
public class CommentController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String idx = req.getParameter("idx");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mode = req.getParameter("mode");
		String id = req.getParameter("id");
		int boardIdx = Integer.parseInt(req.getParameter("board_idx")) ;
		String menu = req.getParameter("menu");
		String comment = req.getParameter("comment");
		String isanswer = req.getParameter("isanswer");
		
		int result = 0;
		
		CommentDTO dto = new CommentDTO();
		dto.setId(id);
		dto.setComments(comment);
		dto.setIsanswer(isanswer);
		
		/*답변 게시글 댓글은 boardIdx가 answerIdx여야 함*/
		String answerIdx = req.getParameter("answer_idx");
		
		if(answerIdx != null) {
			dto.setBoard_fk(Integer.parseInt(answerIdx));
		}else {
			dto.setBoard_fk(boardIdx);
		}
		
		CommentDAO dao = new CommentDAO();
		
		if(mode.equals("write")) {
			result = dao.insertComment(dto);
		}else if(mode.equals("add")) {
			dto.setIscomment(Integer.parseInt(req.getParameter("idx")));
			result = dao.insertAddComment(dto);
		}else if(mode.equals("edit")) {
			dto.setIdx(Integer.parseInt(req.getParameter("idx")));
			result = dao.editComment(dto);
		}
			
		dao.close();
		
		if(result == 1) {		
			resp.sendRedirect("../member/board?cate="+menu+"&mode=v&idx="+boardIdx);
		}else {
			JSFunction.alertBack(resp, "오류");
		}
	}
}
