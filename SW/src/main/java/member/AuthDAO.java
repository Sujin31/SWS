package member;

import java.util.List;
import java.util.Vector;

import common.DBConnPool;

public class AuthDAO extends DBConnPool{
	public AuthDAO() {
		super();
	}
	
	public List<AuthLevelDTO> getAuthLevelsOfMember() {
		List<AuthLevelDTO> arr = new Vector<AuthLevelDTO>();
		String query="SELECT idx, name FROM auth_level WHERE ismng='N'";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				AuthLevelDTO dto = new AuthLevelDTO();
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				arr.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("회원 등급 불러오기 오류");
			e.printStackTrace();
		}
		
		return arr;
	}
}
