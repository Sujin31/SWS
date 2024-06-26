package member.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import common.AuthCheck;
import common.JSFunction;
import common.LoggingDB;
import file.FIleUtil;
import file.FileDAO;
import file.FileDTO;
import manage.MenuDAO;
import manage.MenuDTO;
import member.data.AnswerDAO;
import member.data.AnswerDTO;
import member.data.BoardDAO;
import member.data.BoardDTO;
import member.data.CommentDAO;
import member.data.CommentDTO;
import member.data.HashTagDAO;
import member.data.NoticeDAO;
import member.data.NoticeDTO;
import member.studychat.data.ChatRoomDAO;
import member.studychat.data.ChatRoomDTO;

@WebServlet("/member/boardTmp")
public class BoardTmpController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!AuthCheck.checkMember(req)) {
			resp.sendRedirect("../member/login");
		}else {
		
			HttpSession session = req.getSession();
			
			String mode = req.getParameter("mode");
			String code = req.getParameter("cate");
			String id = (String) session.getAttribute("UserId");
			String isnotice = "N"; //파일 찾기 위해
			//메뉴 정보 불러오기
			MenuDAO MenuDao = new MenuDAO();
			MenuDTO MenuDto = MenuDao.getMenuInfo(code);
			List<String> top = MenuDao.SelectTopMenu(code);
			req.setAttribute("id", id);
			req.setAttribute("MenuDto", MenuDto);
			req.setAttribute("top", top);
			MenuDao.close();
			
			
			//mode l:list v:view w:write e:edit d:delete
			if(mode.equals("l")) { /* 게시물 목록 불러오기 */
				
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
				
				//int start = ( pageNum - 1 ) * pageSize + 1;
				//24.03.15 페이징쿼리변경
				int start = ( pageNum - 1 ) * pageSize;
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
					
					
				}else if(MenuDto.getBoard_tmp().equals("B0004")){
					
					/*
					 * 채팅방
					 * */
					
					//검색어 설정
					String searchStudent = req.getParameter("searchStudent");
					String searchSubject = req.getParameter("searchSubject");
					String title = req.getParameter("title");
					
					if(searchStudent!="" && searchSubject!="") {
						map.put("fcate",searchStudent);
						map.put("scate",searchSubject);
					}
					if(title != "") {
						map.put("title",title);
					}
					
					ChatRoomDAO dao = new ChatRoomDAO();
					totalCount = dao.getTotalRoomCount(map);
					map.put("totalCount", totalCount);
					
					List<ChatRoomDTO> chatList = dao.getChatPage(map);
					dao.close();
					
					String reqUrl = "./board?cate="+code+"&mode=l";
					String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, reqUrl);
					map.put("pagingImg", pagingImg);
					
					req.setAttribute("chatList", chatList);
					req.setAttribute("map", map);
					
					
				}else if(MenuDto.getBoard_tmp().equals("B0005")){
					
					/*
					 * 해시태그
					 * */
					
					map.put("code", code);
					
					BoardDAO dao = new BoardDAO();
					
					totalCount = dao.getBoardCountByHash(map);
					map.put("totalCount", totalCount);
					List<BoardDTO> boardLists =  dao.getBoardPageByHash(map);
					dao.close();
					
					String reqUrl = "./board?cate="+code+"&mode=l";
					String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, reqUrl);
					map.put("pagingImg", pagingImg);
					
					req.setAttribute("boardLists", boardLists);
					req.setAttribute("map", map);
					
				}else {
					
					map.put("code", code);
					
					BoardDAO dao = new BoardDAO();
					
					totalCount = dao.getBoardCount(map);
					map.put("totalCount", totalCount);
					List<BoardDTO> boardLists =  new Vector<BoardDTO>();
					
					if(MenuDto.getBoard_tmp().equals("B0003")) { //QnA게시판 LIST
						//답변 갯수 가져오기
						boardLists = dao.getBoardPageAndAnswer(map);
					}else {
						boardLists = dao.getBoardPage(map);
					}
					
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
				
				if(code.equals("menu001")) {	//공지
					//공지불러오기
					isnotice = "Y";
					NoticeDAO dao = new NoticeDAO();
					NoticeDTO dto = dao.selectNotice(idx);
					dao.viewCountPlus(idx);
					dao.close();
					
					//공지글 엔터처리
					//dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
					req.setAttribute("dto", dto);
					
					//댓글 불러오기
					CommentDAO cdao = new CommentDAO();
					List<CommentDTO> list = cdao.selectComment(idx);
					cdao.close();
					req.setAttribute("comments", list);
					
				}else if(code.equals("menu027")){	//채팅
					//채팅방 입장
					String roomIdx = req.getParameter("idx");
					req.getSession().setAttribute("roomIdx", roomIdx);
					ChatRoomDAO dao =new ChatRoomDAO();
					ChatRoomDTO dto = dao.getChatRoomInfo(idx);
					dao.close();
					
					req.setAttribute("dto", dto);
					
				}else {	//일반
					BoardDAO dao = new BoardDAO();
					BoardDTO dto = dao.selectBoard(idx,code);
					dao.viewCountPlus(idx);
					dao.close();
					
					//dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
	//				dto.setContent(CleanXss.decoding(dto.getContent()));
	//				dto.setTitle(CleanXss.decoding(dto.getTitle()));
					req.setAttribute("dto", dto);
					
					if(MenuDto.getBoard_tmp().equals("B0003")) {	
						//QnA 답변게시글 & 답변-댓글 불러오기
						
						AnswerDAO Adao = new AnswerDAO();
						List<AnswerDTO> answerList = Adao.selectAnswerList(idx);
						Adao.close();
						req.setAttribute("answerList", answerList);
						
						//답변게시글 idx 배열에 저장 후 댓글 불러오기
						ArrayList<Integer> answerIdx = new ArrayList<Integer>();
						for(AnswerDTO tmp : answerList) {
							answerIdx.add(tmp.getIdx());
						}
						CommentDAO cdao = new CommentDAO();
						List<CommentDTO> list = cdao.selectCommentOfAnswer(answerIdx);
						req.setAttribute("comments", list);
						
					}else if(MenuDto.getBoard_tmp().equals("B0005")){
						/*
						 * 해시태그 가져오기
						 * */
						HashTagDAO hdao = new HashTagDAO();
						List<String> tags = hdao.selectHashTagByBoardIdx(idx);
						hdao.close();
						req.setAttribute("tags", tags);
						
					}else {
						//일반 댓글 불러오기
						CommentDAO cdao = new CommentDAO();
						List<CommentDTO> list = cdao.selectComment(idx);
						cdao.close();
						req.setAttribute("comments", list);
					}
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
					
					if(MenuDto.getBoard_tmp().equals("B0005")) {
						HashTagDAO hdao = new HashTagDAO();
						List<String> tags = hdao.selectHashTagByBoardIdx(idx);
						hdao.close();
						req.setAttribute("tags", tags);
					}
					
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
				
			}else if(mode.equals("d")) { /* 삭제 */
				int result = 0;
				int idx = Integer.parseInt(req.getParameter("idx"));
				//log
				LoggingDB logDB = new LoggingDB();
				String methodName = "";
				
				if(code.equals("menu001")) {  //공지게시판
					
					isnotice = "Y";
					NoticeDAO dao = new NoticeDAO();
					result = dao.deleteNotice(id, idx);
					dao.close();
					
					methodName = "deleteNotice";
					
				}else {
					
					//스터디 게시판은 해시태그(매핑)도 함께 지워야함
					if(MenuDto.getBoard_tmp().equals("B0005")) {
						HashTagDAO hdao = new HashTagDAO();
						hdao.deleteHashTag(idx);
						hdao.close();
					}
					
					BoardDAO dao = new BoardDAO();
					result = dao.deleteBoardAndComment(id, idx);
					dao.close();
					
					AnswerDAO adao = new AnswerDAO();
					adao.deleteAnswerAndCommentByBoard(idx);
					adao.close();
					
					methodName = "deleteBoard";
				}
				
				if(result >= 1) {
					
					FileDAO fdao = new FileDAO();
					FileDTO fdto = fdao.getFileInfo(idx, isnotice);
					FIleUtil.deleteFile(req,"/member/Uploads",fdto.getSname());
					fdao.deleteFile(idx, isnotice);
					fdao.close();
					
					logDB.log(req.getSession(), methodName, "success_"+idx);
					JSFunction.alertLocation(resp, "삭제완료", "./board?cate="+code+"&mode=l");
				}else {
					logDB.log(req.getSession(), methodName, "fail_"+idx);
					JSFunction.alertLocation(resp, "삭제오류", "./board?cate="+code+"&mode=l");
				}
			}else if(mode.equals("answer")) {
				int idx = Integer.parseInt(req.getParameter("idx"));
				BoardDAO dao = new BoardDAO();
				BoardDTO dto = dao.selectBoard(idx,code);
				dao.close();
				req.setAttribute("dto", dto);
				
				req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/answer.jsp").forward(req, resp);
				
			}else if(mode.equals("answeredit")) {
				int boardidx = Integer.parseInt(req.getParameter("boardidx"));
				BoardDAO bdao = new BoardDAO();
				BoardDTO bdto = bdao.selectBoard(boardidx,code);
				bdao.close();
				req.setAttribute("boardDto", bdto);
	
				int idx = Integer.parseInt(req.getParameter("idx"));
				AnswerDAO dao = new AnswerDAO();
				AnswerDTO dto = dao.selectAnswer(idx);
				dao.close();
				req.setAttribute("dto", dto);
				
				
				req.getRequestDispatcher("/member/03_board/"+MenuDto.getBoard_tmp()+"/answer_edit.jsp").forward(req, resp);
			}
		}
		
	}
	
}
