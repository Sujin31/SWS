package manage;

import java.util.List;
import java.util.Vector;

import common.DBConnPool;

public class MenuAuthDAO extends DBConnPool{
	public MenuAuthDAO() {
		super();
	}
	public List<MenuAuthDTO> getMenuAuthList(String menu_fk){
		List<MenuAuthDTO> list = new Vector<MenuAuthDTO>();
		String query = "SELECT al.idx, al.NAME , al.ISMNG , ma.MREAD ,ma.MWRITE ,ma.MCOMMENT ,ma.ANSWER"
						+ " FROM AUTH_LEVEL al"
						+ " LEFT JOIN MENU_AUTH ma ON ma.AUTH_LEVEL_FK = al.IDX AND ma.MENU_FK = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, menu_fk);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				MenuAuthDTO dto = new MenuAuthDTO();
				dto.setAuth_level_fk(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setIsmng(rs.getString(3));
				dto.setMread(rs.getString(4));
				dto.setMwrite(rs.getString(5));
				dto.setMcomment(rs.getString(6));
				dto.setAnswer(rs.getString(7));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("메뉴권한 불러오기 오류");
			e.printStackTrace();
		}
		return list;
	}
	
	public int setMenuAuthList(List<MenuAuthDTO> list, String menu) {
		int result = 0;
		
		String delQuery = "DELETE FROM menu_auth WHERE menu_fk=?";
		String inQuery = "INSERT INTO menu_auth (auth_level_fk,menu_fk,mread,mwrite,mcomment,answer) VALUES(?,?,?,?,?,?)";
		
		try {
			psmt = con.prepareStatement(delQuery);
			psmt.setString(1, menu);
			psmt.executeUpdate();
			
			psmt = con.prepareStatement(inQuery);
			for(MenuAuthDTO dto : list) {
				psmt.setString(1, dto.getAuth_level_fk());
				psmt.setString(2, dto.getMenu_fk());
				psmt.setString(3, dto.getMread());
				psmt.setString(4, dto.getMwrite());
				psmt.setString(5, dto.getMcomment());
				psmt.setString(6, dto.getAnswer());

				result = psmt.executeUpdate();
			}
			
		}catch (Exception e) {
			System.out.println("메뉴 권한 추가 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	public MenuAuthDTO getMenuAuthBoard(String auth,String menu) {
		MenuAuthDTO dto = new MenuAuthDTO();
		String query = "SELECT * FROM menu_auth WHERE auth_level_fk=? and menu_fk=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, auth);
			psmt.setString(2, menu);
			rs = psmt.executeQuery();
			if(rs.next()) {
				dto.setAuth_level_fk(auth);
				dto.setMenu_fk(menu);
				dto.setMread(rs.getString("mread"));
				dto.setMwrite(rs.getString("mwrite"));
				dto.setMcomment(rs.getString("mcomment"));
				dto.setAnswer(rs.getString("answer"));
			}
		}catch (Exception e) {
			System.out.println("해당 메뉴 권한 불러오기 오류");
			e.printStackTrace();
		}
		return dto;
	}
}
