package member.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.JSFunction;
import manage.MenuDAO;
import manage.MenuDTO;

@WebServlet("/member/boardTmp")
public class BoardTmpController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		session.setAttribute("id","admin");//지울거
		
		String mode = req.getParameter("mode");
		String code = req.getParameter("cate");
		String id = (String) session.getAttribute("id");
		//메뉴 정보 불러오기
		MenuDAO MenuDao = new MenuDAO();
		MenuDTO MenuDto = MenuDao.getMenuInfo(code);
		List<String> top = MenuDao.SelectTopMenu(code);
		req.setAttribute("MenuDto", MenuDto);
		req.setAttribute("top", top);
		MenuDao.close();
		
		
		//mode l:list v:view w:write e:edit d:delete
		if(mode.equals("l")) {
			
			//검색어 & 페이징
//			String searchField = req.getParameter("searchField");
//			String searchWord = req.getParameter("searchWord");
//			int start = Integer.parseInt(req.getParameter("start"));
//			int end = Integer.parseInt(req.getParameter("end"));
			Map<String, Object> map = new HashMap<String, Object>();
			//map.put("searchField", req.getParameter("searchField"));
			//map.put("searchWord", req.getParameter("searchWord"));
			map.put("start", 1);
			map.put("end", 10);
			map.put("code", code);
			 
			
			/*공지*/
			if(code.equals("menu001")) { //공지게시판은 DB따로 있음
				NoticeDAO dao = new NoticeDAO();
				List<NoticeDTO> mustBoardLists = dao.getMustNoticePage();
				List<NoticeDTO> boardLists =  dao.getNoticePage(map);
				dao.close();
				req.setAttribute("mustBoardLists", mustBoardLists);
				req.setAttribute("boardLists", boardLists);
				req.setAttribute("map", map);
			}else {
				BoardDAO dao = new BoardDAO();
				List<BoardDTO> boardLists =  dao.getBoardPage(map);
				dao.close();
				req.setAttribute("boardLists", boardLists);
				req.setAttribute("map", map);
			}
			req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/list.jsp").forward(req, resp);
		}else if(mode.equals("w")) {
			
			req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/write.jsp").forward(req, resp);
			
		}else if(mode.equals("v")) {
			int idx = Integer.parseInt(req.getParameter("idx"));
			if(code.equals("menu001")) { 
			
				NoticeDAO dao = new NoticeDAO();
				NoticeDTO dto = dao.selectNotice(idx);
				dao.close();
				
				dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
				req.setAttribute("dto", dto);
				
			}else {
				BoardDAO dao = new BoardDAO();
				BoardDTO dto = dao.selectBoard(idx,code);
				dao.close();
				
				dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
				req.setAttribute("dto", dto);
			}
			req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/view.jsp").forward(req, resp);
			
		}else if(mode.equals("e")) {
			
			int idx = Integer.parseInt(req.getParameter("idx"));
			if(code.equals("menu001")) { 
				NoticeDAO dao = new NoticeDAO();
				NoticeDTO dto = dao.selectNotice(idx);
				dao.close();
				
				dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
				req.setAttribute("dto", dto);
				
			}else {
				BoardDAO dao = new BoardDAO();
				BoardDTO dto = dao.selectBoard(idx,code);
				dao.close();
				
				dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
				req.setAttribute("dto", dto);
			}
			req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/edit.jsp").forward(req, resp);
			
		}else if(mode.equals("d")) {
			int result = 0;
			int idx = Integer.parseInt(req.getParameter("idx"));
			if(code.equals("menu001")) { 
				NoticeDAO dao = new NoticeDAO();
				result = dao.deleteNotice(id, idx);
				dao.close();
			}else {
				BoardDAO dao = new BoardDAO();
				result = dao.deleteBoard(id, idx);
				dao.close();
			}
			
			if(result == 1) {
				JSFunction.alertLocation(resp, "삭제완료", "./board?cate="+code+"&mode=l");
			}else {
				JSFunction.alertLocation(resp, "삭제오류", "./board?cate="+code+"&mode=l");
			}
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		String code = req.getParameter("menucode");
		String id = req.getParameter("id");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String notice = req.getParameter("notice"); //필독 여부 Y or N
		//String file = req.getParameter("file"); //파일은 나중에
		
		
		if(mode.equals("w")) {
			/*글쓰기*/
			int result = 0;
			if(code.equals("menu001")) {
				NoticeDAO dao = new NoticeDAO();
				NoticeDTO dto = new NoticeDTO();
				
				dto.setMenu_fk(code);
				dto.setId(id);
				dto.setTitle(title);
				dto.setContent(content);
				dto.setMust(notice);
				
				result = dao.writeNotice(dto);
				dao.close();
			}else {
				BoardDAO dao = new BoardDAO();
				BoardDTO dto = new BoardDTO();
				
				dto.setMenu_fk(code);
				dto.setId(id);
				dto.setTitle(title);
				dto.setContent(content);
				result = dao.writeBoard(dto);
				System.out.println(result);
				
				dao.close();
			}// 공지 or 일반
			
			if(result == 1) {
				JSFunction.alertLocation(resp, "작성되었습니다.", "./board?cate="+code+"&mode=l");
			}else {
				JSFunction.alertLocation(resp, "다시 작성해주세요.", "./board?cate="+code+"&mode=w");
			}
			
		}else if(mode.equals("e")) {
			/*수정*/
			int idx = Integer.parseInt(req.getParameter("idx"));
			int result = 0;
			if(code.equals("menu001")) {
				NoticeDAO dao = new NoticeDAO();
				NoticeDTO dto = new NoticeDTO();
				//dto.setMenu_fk(code);
				dto.setIdx(idx);
				dto.setId(id);
				dto.setTitle(title);
				dto.setContent(content);
				dto.setMust(notice);
				result = dao.editNotice(dto);
				dao.close();
				
				
			}else {
				BoardDAO dao = new BoardDAO();
				BoardDTO dto = new BoardDTO();
				dto.setIdx(idx);
				dto.setMenu_fk(code);
				dto.setId(id);
				dto.setTitle(title);
				dto.setContent(content);
				//dto.setIsreply(isreplay);
				result = dao.editBoard(dto);
				dao.close();
			}
			
			if(result == 1) {
				JSFunction.alertLocation(resp, "수정완료.", "./board?cate="+code+"&mode=v&idx="+idx);
			}else {
				JSFunction.alertLocation(resp, "수정실패.", "./board?cate="+code+"&mode=v&idx="+idx);
			}
		}
	}
}
