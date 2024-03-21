package member.info;

import java.util.List;
import java.util.Vector;

import common.DBConnPool;

public class TodoDAO extends DBConnPool{
	public List<TodoDTO> selectTodoByDate(String id, String date){
		List<TodoDTO> list = new Vector<TodoDTO>();
		String query = "SELECT * FROM todo WHERE id=? AND TODO_DATE=? ORDER BY idx ASC";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, id);
			psmt.setString(2, date);
			rs = psmt.executeQuery();
			while(rs.next()) {
				TodoDTO dto = new TodoDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setContent(rs.getString("content"));
				dto.setTodo_date(rs.getString("todo_date"));
				dto.setIsdone(rs.getString("isdone"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("오늘의 목표 불러오기 오류");
			e.printStackTrace();
		}
		return list;		
	}
	
	public int insertTodo(TodoDTO dto) {
		int result = 0;
		String query = "INSERT INTO TODO (id,todo_date,content) VALUES (?,?,?)";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTodo_date());
			psmt.setString(3, dto.getContent());
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("오늘의 목표 저장 오류");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int updateDoneTodo(int idx,String done) {
		int result = 0;
		String query = "UPDATE TODO SET isdone = ? WHERE idx=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, done);
			psmt.setInt(2, idx);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("완료상태 입력 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateContentTodo(int idx, String content) {
		int result = 0;
		String query = "UPDATE TODO SET content = ? WHERE idx=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, content);
			psmt.setInt(2, idx);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("오늘목표 수정 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteTodo(int idx, String id) {
		int result = 0;
		String query = "DELETE FROM todo WHERE idx=? AND id=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			psmt.setString(2, id);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("오늘목표 삭제 오류");
			e.printStackTrace();
		}
		return result;
	}
}
