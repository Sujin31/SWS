package manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.DBConnPool;

public class MenuDAO extends DBConnPool{
	
	public MenuDAO() {
		super();
	}
	
	//최상위 메뉴 이름 전체 불러오기
	public ArrayList<String> SelectTopMenu() {
		ArrayList<String> arr = new ArrayList<String>();
		String query = "SELECT name FROM menu WHERE pmenu_code IS NULL";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				arr.add(rs.getString(1));
			}
			
		} catch (Exception e) {
			System.out.println("상위메뉴불러오기 오류");
			e.printStackTrace();
		}
		
		return arr;
	}
	
	//현재 메뉴의 상위 메뉴 이름 불러오기
	//dept1,dept2 전부 불러오기
		public ArrayList<String> SelectTopMenu(String code) {
			ArrayList<String> arr = new ArrayList<String>();
			String query = "SELECT t2.name AS top1, t3.name AS top2"
						+ " FROM menu t1"
						+ " LEFT JOIN menu t2 ON t2.code = t1.pmenu_code"
						+ " LEFT JOIN menu t3 ON t3.code = t2.pmenu_code"
						+ " WHERE (t1.CODE = ? )";
			try {
				psmt = con.prepareStatement(query);
				psmt.setString(1, code);
				rs = psmt.executeQuery();
				
				if(rs.next()){
					arr.add(rs.getString(1));
					arr.add(rs.getString(2));
				}
				
			} catch (Exception e) {
				System.out.println("상위메뉴불러오기 오류");
				e.printStackTrace();
			}
			
			return arr;
		}
	
	public JSONArray SelectMenuList(ArrayList<String> toplist) {
		JSONArray result = new JSONArray();
		String query = "SELECT t1.name AS dept1,t1.code AS dept1_code ,t2.name AS dept2 ,t2.code AS dept2_code,t3.name AS dept3 ,t3.code AS dept3_code,"
					+ " CASE WHEN t1.LINK IS NULL AND t2.LINK IS NULL THEN t3.link "
					+ "	WHEN t1.LINK IS NULL THEN t2.LINK "
					+ "	ELSE t1.LINK END AS link"
					+ " FROM menu t1"
					+ " LEFT JOIN menu t2 ON t2.pmenu_code = t1.code"
					+ " LEFT JOIN menu t3 ON t3.pmenu_code = t2.code"
					+ " WHERE t1.name =?"
					+ " ORDER BY t2.LIST_ORDER ,t3.LIST_ORDER";
		try {
			for(String s :toplist) {
				psmt = con.prepareStatement(query);
				psmt.setString(1, s);
				rs = psmt.executeQuery();
				
				
				while(rs.next()) {
					HashMap<String, Object> htmp = new HashMap<String, Object>();
					
					htmp.put("name1",rs.getString(1));
					htmp.put("code1",rs.getString(2));
					htmp.put("name2",rs.getString(3));
					htmp.put("code2",rs.getString(4));
					htmp.put("name3",rs.getString(5));
					htmp.put("code3",rs.getString(6));
					htmp.put("link",rs.getString(7));
					JSONObject tmp = new JSONObject(htmp);
					result.add(tmp);
				}
			}
		} catch (Exception e) {
			System.out.println("메뉴 불러오기 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	
	public int CrateMenu(MenuDTO dto) {
		int result = 0;
		String query = "INSERT INTO menu VALUES(?,?,?,?,?,?,?)";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getCode());
			psmt.setString(2, dto.getPmenu_code());
			psmt.setInt(3, dto.getDepts());
			psmt.setString(4, dto.getName());
			psmt.setString(5, dto.getLink());
			psmt.setString(6, dto.getBoard_tmp());
			psmt.setInt(7, dto.getList_order());
			
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("메뉴 추가 오류");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MenuDTO getMenuInfo(String code) {
		MenuDTO dto = new MenuDTO();
		String query = "SELECT code,name,board_tmp FROM menu WHERE code=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, code);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				dto.setCode(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setBoard_tmp(rs.getString(3));
			}
		} catch (Exception e) {
			System.out.println("메뉴 정보 불러오기 오류");
		}
		return dto;
	}
}
