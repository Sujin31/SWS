package member.studychat;

import member.studychat.data.ChatRoomDAO;
import member.studychat.data.ChatRoomDTO;
import member.studychat.data.ChatUserDTO;

public class ChatRoomManager {
	
//	ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
//	ChatUserDTO chatUserDTO = new ChatUserDTO();
	
	/*
	 * 방 개설
	 * 방장이 개설할 때
	 * @param user_id,name,pass,f_cate,s_cate
	 * */
	public ChatRoomDTO OpenRoom(String id, String name, String pass,String fcate,String scate) {
		
		ChatRoomDTO chatRoomDTO = new ChatRoomDTO(name,pass,fcate,scate,0,id);
		
		//DB 방 개설
		ChatRoomDAO dao = new ChatRoomDAO();
		int idx = dao.insertRoom(chatRoomDTO);
		dao.close();
		
		//방 번호
		chatRoomDTO.setId(idx);
	
		return chatRoomDTO;
	}
	
	/*
	 * 방 폐쇄
	 * 방장이 방 없앨때 || 인원 수 0일 때
	 * */
	public int CloseRoom(int idx) {
		return 10;
	}
	
	/*
	 * 입장
	 * 방장이 개설할 때 || 회원 입장
	 * */
	
	/*
	 * 퇴장
	 * 방장 퇴장 시 방장 교체
	 * */
	
	/*
	 * 방제 변경
	 * */
	
	/*
	 * 비밀방 변경
	 * 비밀번호 변경 || 일반방 변경
	 * */
	
}
