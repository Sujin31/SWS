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
		String query = "SELECT al.idx, al.NAME , al.ISMNG , ma.MREAD ,ma.MWRITE ,ma.MCOMMENT ,ma.REPLY"
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
				dto.setReply(rs.getString(7));
				
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
		String inQuery = "INSERT INTO menu_auth (auth_level_fk,menu_fk,mread,mwrite,mcomment,reply) VALUES(?,?,?,?,?,?)";
		
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
				psmt.setString(6, dto.getReply());

				result = psmt.executeUpdate();
			}
			
		}catch (Exception e) {
			System.out.println("메뉴 권한 추가 오류");
			e.printStackTrace();
		}
		return result;
	}
}
