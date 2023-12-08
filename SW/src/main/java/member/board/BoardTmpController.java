package member.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import common.JSFunction;
import file.FIleUtil;
import file.FileDAO;
import file.FileDTO;
import manage.MenuDAO;
import manage.MenuDTO;

@WebServlet("/member/boardTmp")
public class BoardTmpController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		String mode = req.getParameter("mode");
		String code = req.getParameter("cate");
		String id = (String) session.getAttribute("UserId");
		String isnotice = "N"; //파일 찾기 위해
		//메뉴 정보 불러오기
		MenuDAO MenuDao = new MenuDAO();
		MenuDTO MenuDto = MenuDao.getMenuInfo(code);
		List<String> top = MenuDao.SelectTopMenu(code);
		req.setAttribute("MenuDto", MenuDto);
		req.setAttribute("top", top);
		MenuDao.close();
		
		
		//mode l:list v:view w:write e:edit d:delete
		if(mode.equals("l")) {
			
			//검색어 
			String searchField = req.getParameter("searchField");
			String searchWord = req.getParameter("searchWord");
			int totalCount = 0;
			
			Map<String, Object> map = new HashMap<String, Object>();
			if(searchWord != null) {
				map.put("searchField", searchField);
				map.put("searchWord", searchWord);
			}
			
			//페이징
			ServletContext application = getServletContext();
			int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
			int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
			
			int pageNum = 1;
			String pageTmp = req.getParameter("pageNum");
			if(pageTmp != null &&!pageTmp.equals("")) {
				pageNum = Integer.parseInt(pageTmp);
			}
			
			int start = ( pageNum - 1 ) * pageSize + 1;
			int end =  pageNum * pageSize ;
			map.put("start", start);
			map.put("end", end);
			map.put("pageSize", pageSize);
			map.put("pageNum", pageNum);
			
			/*공지*/
			if(MenuDto.getBoard_tmp().equals("B0001")) { //공지게시판은 DB따로 있음


				NoticeDAO dao = new NoticeDAO();
				
				totalCount = dao.getNoticeCount(map);
				map.put("totalCount", totalCount);
				List<NoticeDTO> mustBoardLists = dao.getMustNoticePage();
				List<NoticeDTO> boardLists =  dao.getNoticePage(map);

				dao.close();
				
				String reqUrl = "./board?cate="+code+"&mode=l";
				String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, reqUrl);
				map.put("pagingImg", pagingImg);
				
				req.setAttribute("mustBoardLists", mustBoardLists);
				req.setAttribute("boardLists", boardLists);
				req.setAttribute("map", map);
			}else {
				map.put("code", code);
				
				BoardDAO dao = new BoardDAO();
				
				totalCount = dao.getBoardCount(map);
				map.put("totalCount", totalCount);
				List<BoardDTO> boardLists =  dao.getBoardPage(map);
				
				dao.close();
				
				String reqUrl = "./board?cate="+code+"&mode=l";
				String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, reqUrl);
				map.put("pagingImg", pagingImg);
				
				req.setAttribute("boardLists", boardLists);
				req.setAttribute("map", map);
			}
			
			req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/list.jsp").forward(req, resp);
			
			
			
		}else if(mode.equals("w")) {
			
			req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/write.jsp").forward(req, resp);
			
			
			
		}else if(mode.equals("v")) {
			
			int idx = Integer.parseInt(req.getParameter("idx"));
			CommentDAO cdao = new CommentDAO();
			List<CommentDTO> list = cdao.selectComment(idx);
			cdao.close();
			req.setAttribute("coments", list);
			if(code.equals("menu001")) { 
				isnotice = "Y";
				NoticeDAO dao = new NoticeDAO();
				NoticeDTO dto = dao.selectNotice(idx);
				dao.viewCountPlus(idx);
				dao.close();
				
				dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
				req.setAttribute("dto", dto);
				
			}else {
				BoardDAO dao = new BoardDAO();
				BoardDTO dto = dao.selectBoard(idx,code);
				dao.viewCountPlus(idx);
				dao.close();
				
				dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
				req.setAttribute("dto", dto);
			}
			//파일 
			FileDAO fdao = new FileDAO();
			FileDTO fdto = fdao.getFileInfo(idx, isnotice);
			req.setAttribute("file", fdto);
			fdao.close();
			
			req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/view.jsp").forward(req, resp);
			
			
			
		}else if(mode.equals("e")) {
			int idx = Integer.parseInt(req.getParameter("idx"));
			if(code.equals("menu001")) { 
				isnotice = "Y";
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
			//파일
			FileDAO fdao = new FileDAO();
			FileDTO fdto = fdao.getFileInfo(idx, isnotice);
			fdao.close();
			req.setAttribute("file", fdto);
			
			req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/edit.jsp").forward(req, resp);
			
		}else if(mode.equals("d")) {
			int result = 0;
			int idx = Integer.parseInt(req.getParameter("idx"));
			if(code.equals("menu001")) { 
				isnotice = "Y";
				NoticeDAO dao = new NoticeDAO();
				result = dao.deleteNotice(id, idx);
				dao.close();
			}else {
				BoardDAO dao = new BoardDAO();
				result = dao.deleteBoard(id, idx);
				dao.close();
			}
			
			if(result == 1) {
				FileDAO fdao = new FileDAO();
				FileDTO fdto = fdao.getFileInfo(idx, isnotice);
				FIleUtil.deleteFile(req,"/member/Updates",fdto.getSname());
				fdao.deleteFile(idx, isnotice);
				fdao.close();
				
				JSFunction.alertLocation(resp, "삭제완료", "./board?cate="+code+"&mode=l");
			}else {
				JSFunction.alertLocation(resp, "삭제오류", "./board?cate="+code+"&mode=l");
			}
		}
		
	}
	
}
