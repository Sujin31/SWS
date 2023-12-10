package member.data;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class NoticeDAO extends DBConnPool{
	public NoticeDAO() {
		super();
	}
	
	public int getNoticeCount(Map<String,Object> map) {
		int totalCount = 0;
		String query = "SELECT COUNT(*) FROM notice ";
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%' AND must = 'N'";
		}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
			
		} catch (Exception e) {
			System.out.println("공지 count오류");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	
	public List<NoticeDTO> getMustNoticePage() {
		List<NoticeDTO> list = new Vector<NoticeDTO>();
		String query = "SELECT * FROM notice WHERE must='Y' ORDER BY idx DESC";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setIsfile(rs.getString("isfile"));
				dto.setViews(rs.getString("views"));
				dto.setRegidate(rs.getDate("regidate"));
				dto.setEditdate(rs.getDate("editdate"));
				dto.setMust(rs.getString("must"));
				
				list.add(dto);
			}
					
		}catch (Exception e) {
			System.out.println("필독 공지 불러오기 오류");
			e.printStackTrace();
		}
		return list;
	}
	
	public List<NoticeDTO> getNoticePage(Map<String, Object> map) {
		List<NoticeDTO> list = new Vector<NoticeDTO>();
		String query = "SELECT * FROM (SELECT tb.*, ROWNUM rNum FROM ( SELECT * FROM notice WHERE";
						
		
		if(map.get("searchWord") != null) {
			query += " " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%' AND";
		}
		
		query +=" must = 'N' ORDER BY idx DESC)tb )WHERE rNum BETWEEN ? and ?";
		
		try {
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setIsfile(rs.getString("isfile"));
				dto.setViews(rs.getString("views"));
				dto.setRegidate(rs.getDate("regidate"));
				dto.setEditdate(rs.getDate("editdate"));
				
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("공지 불러오기 오류");
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int writeNotice(NoticeDTO dto) {
		int result = 0;
		String query = "INSERT INTO notice(idx,menu_fk,id,title,content,isfile,must) VALUES (seq_notice_num.nextval,?,?,?,?,?,?)";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getMenu_fk());
			psmt.setString(2, dto.getId());
			psmt.setString(3, dto.getTitle());
			psmt.setString(4, dto.getContent());
			psmt.setString(5, dto.getIsfile());
			psmt.setString(6, dto.getMust());
			
			result = psmt.executeUpdate(); //insert한 칼럼 idx가지고 오기
			rs = psmt.executeQuery("SELECT seq_notice_num.CURRVAL FROM DUAL");
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("공지게시글 작성 오류");
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public NoticeDTO selectNotice(int idx) {
		NoticeDTO dto = new NoticeDTO();
		String query = "SELECT * FROM notice WHERE idx=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto.setIdx(idx);
				dto.setMenu_fk(rs.getString("menu_fk"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setIsfile(rs.getString("isfile"));
				dto.setViews(rs.getString("views"));
				dto.setRegidate(rs.getDate("regidate"));
				dto.setEditdate(rs.getDate("editdate"));
				dto.setMust(rs.getString("must"));
			}
		} catch (Exception e) {
			System.out.println("공지게시글 불러오기 오류");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	public int editNotice(NoticeDTO dto) {
		int result = 0;
		String query = "UPDATE notice SET title=?, content=?, must=?, isfile=?, editdate=sysdate WHERE idx=? and id =?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getMust());
			psmt.setString(4, dto.getIsfile());
			psmt.setInt(5, dto.getIdx());
			psmt.setString(6, dto.getId());
			
			result = psmt.executeUpdate();
			System.out.println(dto.getIdx());
			System.out.println(dto.getId());
		}catch (Exception e) {
			System.out.println("공지게시글 수정 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteNotice(String id, int idx) {
		int result = 0;
		String query="DELETE FROM notice WHERE idx=? and id=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			psmt.setString(2, id);
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("공지게시글 삭제 오류");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void viewCountPlus(int idx) {
		String query = "UPDATE notice SET views = views+1 WHERE idx=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			psmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
