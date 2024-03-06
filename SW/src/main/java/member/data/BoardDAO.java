package member.data;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;
import oracle.net.aso.q;

public class BoardDAO extends DBConnPool{
	public BoardDAO() {
		super();
	}
	
	public int getBoardCount(Map<String,Object> map) {
		int totalCount = 0;
		String query = "SELECT COUNT(*) FROM board WHERE ";
		if(map.get("searchWord") != null) {
			query += " " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%' and";
		}
		query += " menu_fk='"+map.get("code")+"'";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
			
		} catch (Exception e) {
			System.out.println("게시판 count오류");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	public List<BoardDTO> getBoardPage(Map<String, Object> map) {
		List<BoardDTO> list = new Vector<BoardDTO>();
		String query = "SELECT * FROM (SELECT tb.*, ROWNUM rNum FROM ( SELECT * FROM board WHERE";
						
		
		if(map.get("searchWord") != null) {
			query += " " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%' AND";
		}
		
		query +=" menu_fk=? ORDER BY idx DESC)tb )WHERE rNum BETWEEN ? and ?";
		
		try {
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("code").toString());
			psmt.setString(2, map.get("start").toString());
			psmt.setString(3, map.get("end").toString());
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
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
			System.out.println("게시판 불러오기 오류");
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public int getBoardCountByHash(Map<String,Object> map) {
		int totalCount = 0;
		String query = "SELECT COUNT(*) "
				+ "	FROM ( "
				+ "		SELECT b.IDX, LISTAGG(h.TAGNAME,',') within group (order by h.TAGNAME) tag "
				+ "		FROM BOARD b "
				+ "		FULL OUTER JOIN HASH_MAPPING hm ON hm.BOARD_IDX_FK = b.IDX "
				+ "		FULL OUTER JOIN HASHTAG h ON h.IDX = hm.HASH_IDX_FK "
				+ "		WHERE b.MENU_FK = '"+map.get("code")+"' "
				+ "		GROUP BY b.IDX "
				+ "	)tb";
		
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
			
		} catch (Exception e) {
			System.out.println("게시판 count오류");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	public List<BoardDTO> getBoardPageByHash(Map<String, Object> map) {
		List<BoardDTO> list = new Vector<BoardDTO>();
		String query = "SELECT * FROM ("
						+ "	SELECT tb.*, ROWNUM rNum FROM ( "
						+ "		SELECT b.IDX ,b.ID ,b.TITLE,b.CONTENT ,b.ISFILE ,b.VIEWS ,b.REGIDATE, LISTAGG(h.TAGNAME,',') within group (order by h.TAGNAME) tag "
						+ "		FROM BOARD b "
						+ "		FULL OUTER JOIN HASH_MAPPING hm ON hm.BOARD_IDX_FK = b.IDX "
						+ "		FULL OUTER JOIN HASHTAG h ON h.IDX = hm.HASH_IDX_FK "
						+ "		WHERE b.MENU_FK = ? "
						+ "		GROUP BY b.IDX ,b.ID ,b.TITLE ,b.CONTENT ,b.ISFILE ,b.VIEWS ,b.REGIDATE "
						+ "		ORDER BY b.IDX DESC "
						+ "	)tb ";
				
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		
		query += " )WHERE rNum BETWEEN ? and ?";
		
		try {
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("code").toString());
			psmt.setString(2, map.get("start").toString());
			psmt.setString(3, map.get("end").toString());
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setIsfile(rs.getString("isfile"));
				dto.setViews(rs.getString("views"));
				dto.setRegidate(rs.getDate("regidate"));
				//dto.setEditdate(rs.getDate("editdate"));
				
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("게시판 불러오기 오류");
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<BoardDTO> getBoardPageAndAnswer(Map<String, Object> map) {
		List<BoardDTO> list = new Vector<BoardDTO>();
		String query = "SELECT * \r\n"
					+ "FROM (SELECT tb.*, ROWNUM rNum "
					+ "	FROM ( SELECT b.IDX, b.TITLE, b.id, b.REGIDATE,b.VIEWS ,COUNT(ANSWER) AS answers"
					+ " FROM BOARD b "
					+ " LEFT JOIN ANSWER a ON a.BOARD_FK =b.IDX WHERE";
						
		
		if(map.get("searchWord") != null) {
			query += " b." + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%' AND";
		}
		
		query +=" b.menu_fk=? "
				+ "GROUP BY b.IDX ,b.TITLE,b.id,b.REGIDATE,b.VIEWS "
				+ "ORDER BY idx DESC)tb )WHERE rNum BETWEEN ? and ?";
		
		try {
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("code").toString());
			psmt.setString(2, map.get("start").toString());
			psmt.setString(3, map.get("end").toString());
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setViews(rs.getString("views"));
				dto.setRegidate(rs.getDate("regidate"));
				dto.setAnswers(rs.getInt("answers"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("게시판 불러오기 오류");
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int writeBoard(BoardDTO dto) {
		int result = 0;
		
		String query = "INSERT INTO board(idx,menu_fk,id,title,content,isfile) VALUES (seq_board_num.nextval,?,?,?,?,?)";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getMenu_fk());
			psmt.setString(2, dto.getId());
			psmt.setString(3, dto.getTitle());
			psmt.setString(4, dto.getContent());
			psmt.setString(5, dto.getIsfile());
			rs = psmt.executeQuery(); //insert한 칼럼 idx가지고 오기
//			if(rs.next()) {
//				result = rs.getInt(1);
//			}
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT seq_board_num.CURRVAL FROM DUAL");
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			System.out.println("게시판게시글 작성 오류");
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public BoardDTO selectBoard(int idx,String code) {
		BoardDTO dto = new BoardDTO();
		String query = "SELECT * FROM board WHERE idx=? and menu_fk=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			psmt.setString(2, code);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto.setIdx(idx);
				dto.setMenu_fk(rs.getString("menu_fk"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setIsfile(rs.getString("isfile"));
				dto.setIsreply(rs.getString("isreply"));
				dto.setViews(rs.getString("views"));
				dto.setRegidate(rs.getDate("regidate"));
				dto.setEditdate(rs.getDate("editdate"));
			}
		} catch (Exception e) {
			System.out.println("게시판게시글 불러오기 오류");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	public int editBoard(BoardDTO dto) {
		int result = 0;
		String query = "UPDATE board SET title=?, content=?, isfile=?, editdate=sysdate WHERE idx=? and id =?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getIsfile());
			psmt.setInt(4, dto.getIdx());
			psmt.setString(5, dto.getId());
			
			result = psmt.executeUpdate();
			
			
		}catch (Exception e) {
			System.out.println("게시판게시글 수정 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteBoard(String id, int idx) {
		int result = 0;
		String query="DELETE FROM board WHERE idx=? and id=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			psmt.setString(2, id);
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("게시판게시글 삭제 오류");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void viewCountPlus(int idx) {
		String query = "UPDATE board SET views = views+1 WHERE idx=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			psmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public List<BoardDTO> topFiveBoard() {
		List<BoardDTO> list = new Vector<BoardDTO>();
		String query = "SELECT * "
				+ "FROM (SELECT * "
				+ "		FROM BOARD "
				+ "		WHERE REGIDATE >= ADD_MONTHS(TRUNC(sysdate), -1) AND MENU_FK IN ('menu003','menu004','menu005','menu006') )  "
				+ "JOIN MENU m ON m.CODE = MENU_FK  "
				+ "WHERE ROWNUM <=5 "
				+ "ORDER BY VIEWS DESC, idx DESC";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				BoardDTO tmp = new BoardDTO();
				tmp.setIdx(rs.getInt("idx"));
				tmp.setId(rs.getString("id"));
				tmp.setTitle(rs.getString("title"));
				tmp.setMenu_fk(rs.getString("menu_fk"));
				tmp.setMenu(rs.getString("name"));
				list.add(tmp);
			}
		} catch (Exception e) {
			System.out.println("메인 자유게시판 인기글 불러오기 오류");
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<BoardDTO> getmyQnA(String id) {
		List<BoardDTO> list = new Vector<BoardDTO>();
		String query = "SELECT * "
				+ "FROM (SELECT b.IDX ,b.MENU_FK ,b.TITLE, b.REGIDATE  ,count(a.IDX ) AS answers, m2.NAME AS dept1 ,m.NAME AS dept2 "
				+ "		FROM BOARD b  "
				+ "		FULL OUTER JOIN ANSWER a ON a.BOARD_FK = b.IDX  "
				+ "		JOIN MENU m ON m.CODE = b.MENU_FK  "
				+ "		JOIN MENU m2 ON m2.CODE = m.PMENU_CODE  "
				+ "		WHERE b.id = ? AND m.BOARD_TMP = 'B0003' "
				+ "		GROUP BY b.idx ,b.MENU_FK ,b.TITLE, b.REGIDATE,m2.NAME,m.NAME) "
				+ "WHERE rownum <=5 "
				+ "ORDER BY REGIDATE DESC ";
		try {
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO tmp = new BoardDTO();
				tmp.setIdx(rs.getInt("idx"));
				tmp.setTitle(rs.getString("title"));
				tmp.setMenu_fk(rs.getString("menu_fk"));
				tmp.setAnswers(rs.getInt("answers"));
				tmp.setMenu(rs.getString("dept1"));
				tmp.setMenu2(rs.getString("dept2"));
				list.add(tmp);
			}
		} catch (Exception e) {
			System.out.println("메인 내 QnA 불러오기 오류");
			e.printStackTrace();
		}
		
		return list;
	}
	
	
}
