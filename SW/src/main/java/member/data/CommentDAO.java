package member.data;

import java.sql.Array;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import common.DBConnPool;

public class CommentDAO extends DBConnPool{
	public CommentDAO() {
		super();
	}
	public List<CommentDTO> selectComment(int idx){
		List<CommentDTO> list = new Vector<CommentDTO>();
		String query = "SELECT c.idx ,c.board_fk ,c.id ,c.comments ,c.regidate ,c.editdate ,c.likes ,c.blur ,"
						+ "c2.idx ,c2.board_fk ,c2.id ,c2.comments ,c2.regidate ,c2.editdate ,c2.likes ,c2.blur ,c2.isanswer ,c2.iscomment "
						+ " FROM COMMENTS c"
						+ " LEFT JOIN COMMENTS c2 ON c.IDX =c2.ISCOMMENT "
						+ " WHERE c.BOARD_FK =? AND c.ISCOMMENT IS null AND c.ISANSWER IS NULL ORDER BY c.IDX ";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			rs = psmt.executeQuery();
			while(rs.next()) {
				CommentDTO dto = new CommentDTO();
				dto.setIdx(rs.getInt(1));
				dto.setBoard_fk(rs.getInt(2));
				dto.setId(rs.getString(3));
				dto.setComments(rs.getString(4));
				dto.setRegidate(rs.getDate(5));
				dto.setEditdate(rs.getDate(6));
				dto.setLikes(rs.getInt(7));
				dto.setBlur(rs.getString(8));
				
				dto.setIdx2(rs.getInt(9));
				dto.setBoard_fk2(rs.getInt(10));
				dto.setId2(rs.getString(11));
				dto.setComments2(rs.getString(12));
				dto.setRegidate2(rs.getDate(13));
				dto.setEditdate2(rs.getDate(14));
				dto.setLikes2(rs.getInt(15));
				dto.setBlur2(rs.getString(16));
				dto.setIsanswer2(rs.getString(17));
				dto.setIscomment2(rs.getInt(18));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("댓글 불러오기 오류");
			e.printStackTrace();
		}
		return list;
	}
	
	public int insertComment(CommentDTO dto) {
		int result=0;
		String query = "INSERT INTO comments ( board_fk, id, comments, regidate, isanswer) VALUES( ?, ?, ?, CURRENT_DATE,?)";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, dto.getBoard_fk());
			psmt.setString(2, dto.getId());
			psmt.setString(3, dto.getComments());
			psmt.setString(4, dto.getIsanswer());
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("댓글 추가 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public int insertAddComment(CommentDTO dto) {
		int result=0;
		String query = "INSERT INTO comments ( board_fk, id, comments, regidate, isanswer, iscomment) VALUES(?, ?, ?, CURRENT_DATE,?,?)";

		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, dto.getBoard_fk());
			psmt.setString(2, dto.getId());
			psmt.setString(3, dto.getComments());
			psmt.setString(4, dto.getIsanswer());
			psmt.setInt(5, dto.getIscomment());
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("대댓글 추가 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public int editComment(CommentDTO dto) {
		int result=0;
		String query = "UPDATE comments SET comments=? , editdate=CURRENT_DATE WHERE idx=? and id=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getComments());
			psmt.setInt(2, dto.getIdx());
			psmt.setString(3, dto.getId());
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("댓글 수정 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public List<CommentDTO> selectCommentOfAnswer(ArrayList<Integer> idx){
		List<CommentDTO> list = new Vector<CommentDTO>();
		String query = "SELECT c.idx ,c.board_fk ,c.id ,c.comments ,c.regidate ,c.editdate ,c.likes ,c.blur ,"
						+ "c2.idx ,c2.board_fk ,c2.id ,c2.comments ,c2.regidate ,c2.editdate ,c2.likes ,c2.blur ,c2.isanswer ,c2.iscomment "
						+ " FROM COMMENTS c"
						+ " LEFT JOIN COMMENTS c2 ON c.IDX =c2.ISCOMMENT "
						+ " WHERE c.BOARD_FK IN (%s) AND c.ISCOMMENT IS null AND c.ISANSWER ='Y' ORDER BY c.IDX ";
		String sql = String.format(query, idx.stream().map(String::valueOf).collect(Collectors.joining(", ")));
		try {
			psmt = con.prepareStatement(sql);
			//Array idxArr = con.createArrayOf("int_array", idx.toArray());
			//psmt.setArray(1, idxArr);
			rs = psmt.executeQuery();
			while(rs.next()) {
				CommentDTO dto = new CommentDTO();
				dto.setIdx(rs.getInt(1));
				dto.setBoard_fk(rs.getInt(2));
				dto.setId(rs.getString(3));
				dto.setComments(rs.getString(4));
				dto.setRegidate(rs.getDate(5));
				dto.setEditdate(rs.getDate(6));
				dto.setLikes(rs.getInt(7));
				dto.setBlur(rs.getString(8));
				
				dto.setIdx2(rs.getInt(9));
				dto.setBoard_fk2(rs.getInt(10));
				dto.setId2(rs.getString(11));
				dto.setComments2(rs.getString(12));
				dto.setRegidate2(rs.getDate(13));
				dto.setEditdate2(rs.getDate(14));
				dto.setLikes2(rs.getInt(15));
				dto.setBlur2(rs.getString(16));
				dto.setIsanswer2(rs.getString(17));
				dto.setIscomment2(rs.getInt(18));
				
				list.add(dto);
			}
		}catch (SQLSyntaxErrorException e) {
			System.out.println("답변없음");
		}catch (Exception e) {
			System.out.println("댓글 불러오기 오류");
			e.printStackTrace();
		}
		return list;
	}
	
	public int deleteComment(int idx) {
		int result = 0;
		String query = "DELETE FROM comments WHERE idx=? or ISCOMMENT = ?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("댓글 삭제 오류");
			e.printStackTrace();
		}
		
		return result;
	}
}
