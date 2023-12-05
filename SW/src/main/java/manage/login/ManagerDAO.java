package manage.login;

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
}
