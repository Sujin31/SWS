package member.data;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;

import common.DBConnPool;

public class AnswerDAO extends DBConnPool{
	public AnswerDAO() {
		super();
	}
	public List<AnswerDTO> selectAnswerList(int boardIdx){
		List<AnswerDTO> list = new Vector<AnswerDTO>();
		String query ="SELECT * FROM answer WHERE board_fk = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, boardIdx);
			rs = psmt.executeQuery();
			while(rs.next()) {
				AnswerDTO dto = new AnswerDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setBoard_fk(rs.getInt("board_fk"));
				dto.setId(rs.getString("id"));
				dto.setAnswer(rs.getString("answer"));
				dto.setRegidate(rs.getDate("regidate"));
				dto.setEditdate( rs.getDate("editdate"));
				dto.setLikes(rs.getInt("likes"));
				dto.setBlur(rs.getString("blur"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("답변 게시물 불러오기 오류");
			e.printStackTrace();
		}
		
		return list;
	}
	
	public AnswerDTO selectAnswer(int idx){
		AnswerDTO dto = new AnswerDTO();
		String query ="SELECT * FROM answer WHERE idx = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			rs = psmt.executeQuery();
			if(rs.next()) {
				dto.setIdx(rs.getInt("idx"));
				dto.setBoard_fk(rs.getInt("board_fk"));
				dto.setId(rs.getString("id"));
				dto.setAnswer(rs.getString("answer"));
				dto.setRegidate(rs.getDate("regidate"));
				dto.setEditdate( rs.getDate("editdate"));
				dto.setLikes(rs.getInt("likes"));
				dto.setBlur(rs.getString("blur"));
			}
		} catch (Exception e) {
			System.out.println("답변 게시물 불러오기 오류");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	public int insertAnswer(AnswerDTO dto) {
		int result = 0;
		String query = "INSERT INTO answer ( board_fk, id, answer,regidate) VALUES(?,?,?,CURRENT_DATE)";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, dto.getBoard_fk());
			psmt.setString(2, dto.getId());
			psmt.setString(3, dto.getAnswer());
			result = psmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("답변 게시물 insert오류");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int editAnswer(AnswerDTO dto) {
		int result = 0;
		String query = "UPDATE answer SET answer=? ,editdate=CURRENT_DATE where idx=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getAnswer());
			psmt.setInt(2, dto.getIdx());
			result = psmt.executeUpdate();
					
		} catch (Exception e) {
			System.out.println("답변 게시물 update오류");
			e.printStackTrace();
		}
		return result;
				
	}
	
	//답변 게시글만 지우기
	public int deleteAnswer(int idx) {
		int result = 0;
		String query = "delete from answer where idx = " + idx;
		
		try {
			stmt = con.createStatement();
			result = stmt.executeUpdate(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	//답변 지울 때 댓글도 같이 지워짐
	public int deleteAnswerAndComment(String id, int idx) {
		int result = 0;
		//String query = "DELETE FROM answer WHERE idx=?";
		String query = "delete "
						+ "from a,c "
						+ "using answer a "
						+ "left join comments c on a.IDX = c.BOARD_FK and c.isanswer = 'Y'"
						+ "where a.IDX = ?"	;
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("답변 게시물 delete 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	//질문 게시글 지울 때 답변 & 댓글 같이 지워짐
	public int deleteAnswerAndCommentByBoard(int b_idx) {
		int result = 0;
		String query = "delete "
						+ "from a,c "
						+ "using answer a "
						+ "left join comments c on a.IDX = c.BOARD_FK and c.isanswer = 'Y'"
						+ "where a.board_fk = ?"	;
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, b_idx);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("답변 게시물 delete 오류");
			e.printStackTrace();
		}
		return result;
	}
}
