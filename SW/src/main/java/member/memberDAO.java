package member;

import common.DBConnPool;

public class memberDAO extends DBConnPool{
	//로그인
	public memberDAO() {
		super();
	}
	
	public memberDTO memberLogin(String id, String pw) {
		memberDTO dto = new memberDTO();
		String query = "SELECT id,name FROM user_info WHERE id = ? and password = ?";
		try {
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if(rs.next()) {
				dto.setId(rs.getString(1));
				dto.setName(rs.getString(2));
			}
			
		} catch (Exception e) {
			System.out.println("로그인 오류");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	public boolean checkId(String id) {
		boolean flag = false;
		String query = "SELECT COUNT(*) FROM user_info WHERE id = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,id);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt(1) == 0) {
					flag = true; //아이디 사용 가능
				}
			}
			
		}catch (Exception e) {
			System.out.println("아이디 중복 체크 오류");
			e.printStackTrace();
		}
		
		return flag;
	}
	public boolean checkPhoneNum(String num) {
		boolean flag = false;
		String query = "SELECT COUNT(*) FROM user_info WHERE phone = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,num);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt(1) == 0) {
					flag = true; //아이디 사용 가능
				}
			}
			
		}catch (Exception e) {
			System.out.println("전화번호 중복 체크 오류");
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public int signUp(memberDTO dto) {
		int result = 0;
		String query = "INSERT INTO user_info (id,name,birth,gender,password,auth_level_fk,phone) VALUES (?,?,?,?,?,?,?)";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getBirth());
			psmt.setString(4, dto.getGender());
			psmt.setString(5, dto.getPassword());
			psmt.setString(6, dto.getAuth_level_fk());
			psmt.setString(7, dto.getPhone());
			
			result = psmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("회원가입 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public String findId(String name, String phone) {
		String findid = "";
		String query = "SELECT id FROM user_info WHERE name=? AND phone=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, name);
			psmt.setString(2, phone);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				findid = rs.getString(1);
			}
			
		} catch (Exception e) {
			System.out.println("아이디찾기 오류");
			e.printStackTrace();
		}
		return findid;
	}
	
	public String findPw(String id, String name, String phone) {
		String memId = "";
		String query = "SELECT id FROM user_info WHERE id=? AND name=? AND phone=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, id);
			psmt.setString(2, name);
			psmt.setString(3, phone);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				memId = rs.getString(1);
			}
			
		} catch (Exception e) {
			System.out.println("비밀번호찾기 오류");
			e.printStackTrace();
		}
		return memId;
	}
	
	public int resetPw(String id, String pw) {
		int result = 0;
		String query = "UPDATE user_info SET password= ? WHERE id=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, pw);
			psmt.setString(2, id);
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("비밀번호 재설정 오류");
			e.printStackTrace();
		}
		return result;
	}
}
