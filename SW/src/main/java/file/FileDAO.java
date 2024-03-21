package file;

import common.DBConnPool;

public class FileDAO extends DBConnPool{
	public FileDAO() {
		super();
	}
	
	public int fileUpload(FileDTO dto) {
		int result = 0;
		String query = "INSERT INTO file_info (board_fk, oname, sname, fpath, count, isnotice) values(?,?,?,?,0,?)";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, dto.getBoard_fk());
			psmt.setString(2,dto.getOname());
			psmt.setString(3, dto.getSname());
			psmt.setString(4, dto.getFpath());
			psmt.setString(5, dto.getIsnotice());
			
			result = psmt.executeUpdate();
					
		} catch (Exception e) {
			System.out.println("파일 업로드 오류");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public FileDTO getFileInfo(int idx, String isnotice) {
		FileDTO dto = new FileDTO();
		String query = "SELECT * FROM file_info WHERE board_fk = ? and isnotice =?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, idx);
			psmt.setString(2, isnotice);
			rs = psmt.executeQuery();
			if(rs.next()) {
				dto.setIdx(rs.getInt("idx"));
				dto.setBoard_fk(rs.getInt("board_fk"));
				dto.setOname(rs.getString("oname"));
				dto.setSname(rs.getString("sname"));
				dto.setFpath(rs.getString("fpath"));
				dto.setCount(rs.getInt("idx"));
				dto.setIsnotice(rs.getString("isnotice"));
			}
		} catch (Exception e) {
			System.out.println("파일 불러오기 오류");
			e.printStackTrace();
		}
		return dto;
	}
	
	public void downCountPlus(int idx) {
		String qurey = "UPDATE file_info SET count=count+1 WHERE idx=?";
		try {
			psmt = con.prepareStatement(qurey);
			psmt.setInt(1, idx);
			psmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void deleteFile(int idx,String isnotice) {
		String qurey = "DELETE FROM file_info WHERE board_fk=? and isnotice=?";
		try {
			psmt = con.prepareStatement(qurey);
			psmt.setInt(1, idx);
			psmt.setString(2, isnotice);
			psmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public int editFile(FileDTO dto) {
		int result = 0;
		String query = "UPDATE file_info SET oname=?, sname=?, count=0 WHERE board_fk=? and isnotice=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getOname());
			psmt.setString(2, dto.getSname());
			psmt.setInt(3, dto.getBoard_fk());
			psmt.setString(4, dto.getIsnotice());
			result = psmt.executeUpdate();
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("파일 수정 오류");
			e.printStackTrace();
		}
		return result;
	}
}
