package member.data;

import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import common.DBConnPool;

public class HashTagDAO extends DBConnPool{
	public HashTagDAO() {
		super();
	}
	
	/*
	 * 게시물 해시태그 검색 By board_idx
	 * */
	public List<String> selectHashTagByBoardIdx(int board_idx){
		List<String> list = new Vector<String>();
		String query = "SELECT tagname "
				+ "FROM HASHTAG h "
				+ "JOIN HASH_MAPPING hm ON hm.HASH_IDX_FK = h.IDX "
				+ "WHERE hm.BOARD_IDX_FK = " + board_idx + " "
				+ "ORDER BY h.IDX ";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	/*
	 * 중복 해시태그 찾기(insert 전 단계)
	 * */
	public List<String> selectDupHashTag(List<String> tags){
		List<String> Dup = new Vector<String>();
		String query = "SELECT TAGNAME FROM HASHTAG WHERE TAGNAME IN ('%s')";
		String sql = String.format(query, tags.stream().map(String::valueOf).collect(Collectors.joining("', '"))); //공부
		
		try {
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Dup.add(rs.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Dup;
	}
	
	/*
	 * 태그이름으로 해시 테이블 pk검색
	 * */
	public int selectIdxByTagName(String name) {
		int result = 0;
		String query = "SELECT idx FROM hashtag WHERE tagname = '"+name+"'";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public void insertHashTag(int board_idx,List<String> tags, List<String> dupTags) {
		int tag_idx = 0;
		String query = "INSERT INTO hashtag(tagname) VALUES(?)";
		try {
			//hashtag 테이블에 입력
			for(String tag : tags) {
				if(!dupTags.contains(tag)) { //중복아닌것만
					//hashtag 테이블에 넣기
					psmt = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
					psmt.setString(1, tag);
					psmt.executeUpdate();
					rs = psmt.getGeneratedKeys();
					if(rs.next()) {
						tag_idx = rs.getInt(1);
					}
				}else { //중복인 경우는 idx만 가져옴
					tag_idx = selectIdxByTagName(tag);
				}
				
				//hashtag 매핑
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO hash_mapping(board_idx_fk,hash_idx_fk) VALUES("+board_idx+","+tag_idx+")");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteHashTag(int board_idx) {
//		String query1 = "DELETE "
//						+ "FROM HASHTAG h "
//						+ "WHERE EXISTS (SELECT * FROM HASH_MAPPING hm WHERE hm.HASH_IDX_FK = h.IDX AND hm.BOARD_IDX_FK="+board_idx+")";
		String query = "DELETE FROM HASH_MAPPING WHERE BOARD_IDX_FK = " + board_idx;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
