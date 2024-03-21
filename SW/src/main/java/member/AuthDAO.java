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
	
	public List<AuthLevelDTO> getAuthLevelsOfManager() {
		List<AuthLevelDTO> arr = new Vector<AuthLevelDTO>();
		String query="SELECT idx, name FROM auth_level WHERE ismng='Y'";
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
			System.out.println("관리자 등급 불러오기 오류");
			e.printStackTrace();
		}
		
		return arr;
	}
	
	public List<AuthLevelDTO> getAuthLevelsAll() {
		List<AuthLevelDTO> arr = new Vector<AuthLevelDTO>();
		String query="SELECT * FROM auth_level order by level";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				AuthLevelDTO dto = new AuthLevelDTO();
				dto.setIdx(rs.getString("idx"));
				dto.setIsmng(rs.getString("ismng"));
				dto.setName(rs.getString("name"));
				dto.setLevel(rs.getInt("level"));
				arr.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("등급 불러오기 오류");
			e.printStackTrace();
		}
		
		return arr;
	}
	
	public int setAuthLevel(AuthLevelDTO dto) {
		int result = 0;
		String query = "INSERT INTO auth_level VALUES(?,?,?)";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getIdx());
			psmt.setString(2, dto.getIsmng());
			psmt.setString(3, dto.getName());
			
			result = psmt.executeUpdate();
		}catch (Exception e) {
			System.out.println("권한 추가 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteAuthLevel(String idx) {
		int result = 0;
		String query = "DELETE FROM auth_level WHERE idx=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("권한 삭제 오류");
			e.printStackTrace();
		}
		return result;
	}
}
