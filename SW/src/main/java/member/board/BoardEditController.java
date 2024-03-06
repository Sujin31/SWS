package member.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import common.JSFunction;
import file.FIleUtil;
import file.FileDAO;
import file.FileDTO;
import member.data.BoardDAO;
import member.data.BoardDTO;
import member.data.HashTagDAO;
import member.data.NoticeDAO;
import member.data.NoticeDTO;

@WebServlet("/member/boardedit")
public class BoardEditController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//파일업로드 처리
		String saveDir = req.getServletContext().getRealPath("/member/Uploads");
		ServletContext application = getServletContext();
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));
		
		MultipartRequest mr = FIleUtil.uploadFile(req, saveDir, maxPostSize);
		if(mr == null) {
			JSFunction.alertBack(resp, "첨부 파일이 제한 용량을 초과합니다.");
			return;
		}
		int idx = Integer.parseInt(mr.getParameter("idx"));
		String boardTmp = mr.getParameter("boardTmp");
		String code = mr.getParameter("menucode");
		String id = mr.getParameter("id");
		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		String isfile = mr.getParameter("isfile");
		String isnotice = "N";
		int result = 0;
		if(boardTmp.equals("B0001")) {
			/*   공지   */
			String notice = mr.getParameter("notice"); //필독 여부 Y or N
			isnotice = "Y";
			NoticeDAO dao = new NoticeDAO();
			NoticeDTO dto = new NoticeDTO();
			dto.setIdx(idx);
			dto.setId(id);
			dto.setTitle(title);
			dto.setContent(content);
			dto.setMust(notice);
			dto.setIsfile(isfile);
			
			result = dao.editNotice(dto);
			dao.close();
			
			
		}else {
			
			/*
			 * 해시태그 전부 지우고 새로 넣기
			 * */
			if(boardTmp.equals("B0005")) {
				HashTagDAO hdao = new HashTagDAO();
				hdao.deleteHashTag(idx);
				String[] tag = mr.getParameterValues("tag");
				List<String> tags = Arrays.asList(tag);
				
				HashTagDAO hasgDao = new HashTagDAO();
				
				List<String> dupTags = hasgDao.selectDupHashTag(tags);
				
				//hash 넣기
				hasgDao.insertHashTag(idx, tags, dupTags);
				hdao.close();
			}
			
			BoardDAO dao = new BoardDAO();
			BoardDTO dto = new BoardDTO();
			dto.setIdx(idx);
			dto.setId(id);
			dto.setTitle(title);
			dto.setContent(content);
			dto.setIsfile(isfile);
			
			result = dao.editBoard(dto);
			dao.close();
		}
		
		if(result == 0) {
			JSFunction.alertBack(resp, "수정 실패");
		}
		
		String fileName = mr.getFilesystemName("file");
		if(fileName != null) {
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = now+ext;
			
			//저장할 파일명으로 변경
			File oldFile = new File(saveDir+File.separator+fileName);
			File newFile = new File(saveDir+File.separator+newFileName);
			oldFile.renameTo(newFile);
			
			FileDTO dto = new FileDTO();
			dto.setBoard_fk(idx);
			dto.setOname(fileName);
			dto.setSname(newFileName);
			dto.setFpath(saveDir);
			dto.setIsnotice(isnotice);
			
			FileDAO dao = new FileDAO();
			int fres = dao.editFile(dto);
			dao.close();
			
			if(fres == 0) {
				JSFunction.alertBack(resp, "수정 실패");
			}
		}
			
		JSFunction.alertLocation(resp, "수정완료.", "./board?cate="+code+"&mode=v&idx="+idx);
		
	}
}
