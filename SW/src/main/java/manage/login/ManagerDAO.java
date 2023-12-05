package manage.login;

import java.util.List;
import java.util.Vector;

import common.DBConnPool;

public class ManagerDAO extends DBConnPool{
	public ManagerDAO() {
		super();
	}
	
	public ManagerDTO managerLogin(String id, String pw) {
		ManagerDTO dto = new ManagerDTO();
		String query = "SELECT id,name,auth_level_fk FROM manager_info WHERE id = ? and password = ?";
		try {
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if(rs.next()) {
				dto.setId(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setAuth_level_fk(rs.getString(3));
			}
			
		} catch (Exception e) {
			System.out.println("로그인 오류");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	public List<ManagerDTO> getManagerList(){
		List<ManagerDTO> list = new Vector<ManagerDTO>();
		String query = "SELECT * FROM manager_info";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				ManagerDTO dto = new ManagerDTO();
				dto.setId(rs.getString("id"));
				dto.setAuth_level_fk(rs.getString("auth_level_fk"));
				dto.setName(rs.getString("name"));
				dto.setPhone(rs.getString("phone"));
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("관리자 목록 불러오기 오류");
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int setManager(ManagerDTO dto) {
		int result = 0;
		String query="INSERT INTO manager_info VALUES(?,?,?,?,?)";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getAuth_level_fk());
			psmt.setString(3, dto.getPassword());
			psmt.setString(4, dto.getName());
			psmt.setString(5, dto.getPhone());
			
			result = psmt.executeUpdate();
					
		} catch (Exception e) {
			System.out.println("관리자 추가 오류");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int delManager(String id) {
		int result = 0;
		String query = "DELETE FROM manager_info WHERE id=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, id);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("관리자 삭제 오류");
			e.printStackTrace();
		}
		return result;
	}
}
