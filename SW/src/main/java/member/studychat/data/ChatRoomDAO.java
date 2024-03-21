package member.studychat.data;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class ChatRoomDAO extends DBConnPool{
	public ChatRoomDAO() {
		super();
	}
	
	//전체 채팅방 수
	public int getTotalRoomCount(Map<String,Object> map) {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM chatroom ";
		if(map.get("fcate") != null && map.get("scate") != null) {
			sql += "WHERE firstcate = '" + map.get("fcate") + "' and "
					+ " secondcate = '" + map.get("scate") + "' ";
			
			if(map.get("title") != null) {
				sql += " and name like '%"+map.get("title")+"%'";
			}
		}
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println("채팅방 전체 조회 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	//채팅방 페이징
	public List<ChatRoomDTO> getChatPage(Map<String,Object> map){
		List<ChatRoomDTO> list = new Vector<ChatRoomDTO>();
		String sql = "SELECT * FROM chatroom ";
						
		if(map.get("fcate") != null && map.get("scate") != null) {
			sql += " WHERE firstcate = '" + map.get("fcate") + "' and "
					+ " secondcate = '" + map.get("scate") + "' ";
			
			if(map.get("title") != null) {
				sql += " and name like '%"+map.get("title")+"%'";
			}
		}
		sql +=" ORDER BY id desc limit 10 offset ?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, (int) map.get("start"));
			rs = psmt.executeQuery();
			while(rs.next()) {
				ChatRoomDTO tmp = new ChatRoomDTO(rs.getInt("id"),rs.getString("name"),rs.getString("pass"),
													rs.getString("firstcate"),rs.getString("secondcate"),rs.getInt("participant"),rs.getString("owner"));
				list.add(tmp);
			}
		} catch (Exception e) {
			System.out.println("채팅방 조회 오류");
			e.printStackTrace();
		}
		
		return list;
	}
	
	//채팅방 정보
	public ChatRoomDTO getChatRoomInfo(int idx) {
		ChatRoomDTO dto = new ChatRoomDTO();
		String sql = "SELECT * FROM chatroom WHERE id="+idx;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				dto.setId(idx);
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setFcate(rs.getString("firstcate"));
				dto.setScate(rs.getString("secondcate"));
				dto.setOwner(rs.getString("owner"));
				dto.setParticipant(rs.getInt("participant"));
			}
		}catch (Exception e) {
			System.out.println("방 정보 조회 오류");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	//채팅방 만들기
	public int insertRoom(ChatRoomDTO dto) {
		int result = 0;
		String sql = "INSERT INTO chatroom (name,pass,firstcate,secondcate,participant,owner) VALUES(?,?,?,?,?,?)";
		
		try {
			psmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getPass());
			psmt.setString(3, dto.getFcate());
			psmt.setString(4, dto.getScate());
			psmt.setInt(5, dto.getParticipant());
			psmt.setString(6, dto.getOwner());
			
			psmt.executeUpdate();
			
			rs = psmt.getGeneratedKeys();
			if(rs.next()) {
				result = rs.getInt(1);
				String sql_log = "insert into chatroom_log select * from chatroom where id="+result ;
				stmt = con.createStatement();
				stmt.executeUpdate(sql_log);
			}
			
		} catch (Exception e) {
			System.out.println("방 개설 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	//채팅방 참여자수 증가(입장)
	public void PlayerCountUp(int idx) {
		String sql = "UPDATE chatroom SET participant = participant+1 WHERE id = "+idx;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		}catch (Exception e) {
			System.out.println("참가자수 증가 오류");
			e.printStackTrace();
		}
	}
	
	//채팅방 참여자수 감소(퇴장)
	public void PlayerCountDown(int idx) {
		String sql = "UPDATE chatroom SET participant = participant-1 WHERE id = "+idx;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		}catch (Exception e) {
			System.out.println("참가자수 감소 오류");
			e.printStackTrace();
		}
	}
	
	//참여자수 없는 채팅방 폐쇄
	public void DeleteZeroPlayer(int idx) {
		String sql = "DELETE FROM chatroom WHERE participant<=0 and id ="+idx;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("0명방 폐쇄");
			e.printStackTrace();
		}
	}
	
	//유저 채팅로그 저장
	public void insertUserChat(int roomNum, String id, String chat) {
		String sql = "INSERT INTO chat_log (id,roomnum,comments) VALUES (?,?,?)";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setInt(2, roomNum);
			psmt.setString(3,chat);
			psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("채팅저장 오류");
			e.printStackTrace();
		}
	}
	
	//채팅방 비밀번호 가져오기
	public String getPass(int idx) {
		String pass = "";
		String sql = "SELECT pass FROM chatroom WHERE id="+idx;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				pass = rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println("비밀번호 가져오기 오류");
			e.printStackTrace();
		}
		return pass;
	}
	
	//채팅방 인원 수 가져오기
	public int getPlayers(int idx) {
		int total=0;
		String sql = "SELECT participant FROM chatroom WHERE id="+idx;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("인원수 가져오기 오류");
			e.printStackTrace();
		}
		return total;
	}
}
