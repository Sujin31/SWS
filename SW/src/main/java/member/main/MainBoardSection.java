package member.main;

import java.util.List;
import java.util.Vector;

import member.data.BoardDAO;
import member.data.BoardDTO;
import member.data.NoticeDAO;
import member.data.NoticeDTO;

public class MainBoardSection {
	
	public List<NoticeDTO> getMainMustNotices(){
		NoticeDAO ndao = new NoticeDAO();
		List<NoticeDTO> mNotices = ndao.topFiveNoticeMust();
		ndao.close();
		
		return mNotices;
	}
	public List<NoticeDTO> getMainNotices(int size){
		NoticeDAO ndao = new NoticeDAO();
		List<NoticeDTO> notices = ndao.topFiveNotice(size);
		ndao.close();
		
		return notices;
	}
	
	public List<BoardDTO> getTopFiveBoard(){
		BoardDAO dao = new BoardDAO();
		List<BoardDTO> list = dao.topFiveBoard();
		dao.close();
		
		return list;
	}
	
	public List<BoardDTO> getMyQnA(String id){
		BoardDAO dao = new BoardDAO();
		List<BoardDTO> list = dao.getmyQnA(id);
		dao.close();
		
		return list;
	}
}
