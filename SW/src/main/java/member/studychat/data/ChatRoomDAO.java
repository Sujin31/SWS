package member.studychat.data;

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
		String sql = "SELECT * FROM (SELECT tb.*, ROWNUM rNum FROM ( SELECT * FROM chatroom ";
						
		if(map.get("fcate") != null && map.get("scate") != null) {
			sql += " WHERE firstcate = '" + map.get("fcate") + "' and "
					+ " secondcate = '" + map.get("scate") + "' ";
		}
		sql +=" ORDER BY id DESC)tb )WHERE rNum BETWEEN ? and ?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();
			while(rs.next()) {
				ChatRoomDTO tmp = new ChatRoomDTO(rs.getInt("id"),rs.getString("name"),rs.getString("pass"),
													rs.getString("firstcate"),rs.getString("secondcate"),rs.getInt("player"),rs.getString("owner"));
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
				dto.setPlayer(rs.getInt("player"));
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
		String sql = "INSERT INTO chatroom VALUES(seq_chat_num.nextval,?,?,?,?,?,?,sysdate)";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getPass());
			psmt.setString(3, dto.getFcate());
			psmt.setString(4, dto.getScate());
			psmt.setInt(5, dto.getPlayer());
			psmt.setString(6, dto.getOwner());
			
			psmt.executeQuery();
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT seq_chat_num.CURRVAL FROM DUAL");
			if(rs.next()) {
				result = rs.getInt(1);
				String sql_log = "INSERT INTO CHATROOM_LOG cl SELECT * FROM CHATROOM c WHERE c.ID ="+result;
				stmt = con.createStatement();
				stmt.executeQuery(sql_log);
			}
			
		} catch (Exception e) {
			System.out.println("방 개설 오류");
			e.printStackTrace();
		}
		return result;
	}
	
	//채팅방 참여자수 증가(입장)
	public void PlayerCountUp(int idx) {
		String sql = "UPDATE chatroom SET player = player+1 WHERE id = "+idx;
		try {
			stmt = con.createStatement();
			stmt.executeQuery(sql);
		}catch (Exception e) {
			System.out.println("참가자수 증가 오류");
			e.printStackTrace();
		}
	}
	
	//채팅방 참여자수 감소(퇴장)
	public void PlayerCountDown(int idx) {
		String sql = "UPDATE chatroom SET player = player-1 WHERE id = "+idx;
		try {
			stmt = con.createStatement();
			stmt.executeQuery(sql);
		}catch (Exception e) {
			System.out.println("참가자수 감소 오류");
			e.printStackTrace();
		}
	}
	
	//참여자수 없는 채팅방 폐쇄
	public void DeleteZeroPlayer(int idx) {
		String sql = "DELETE chatroom WHERE player<=0 and id ="+idx;
		try {
			stmt = con.createStatement();
			stmt.executeQuery(sql);
		} catch (Exception e) {
			System.out.println("0명방 폐쇄");
			e.printStackTrace();
		}
	}
	
	//유저 채팅로그 저장
	public void insertUserChat(int idx, String id, String chat) {
		String sql = "INSERT INTO chat_log VALUES (seq_chatcomment_num.nextval,?,?,?,sysdate)";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setInt(2, idx);
			psmt.setString(3,chat);
			psmt.executeQuery();
			
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
		String sql = "SELECT player FROM chatroom WHERE id="+idx;
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
