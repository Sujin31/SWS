package member.studychat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomManager {
	private static List<StudyRoom> roomList;
	private static AtomicInteger seq_room_num;
	
	static {
	        roomList = new ArrayList<>();
	        seq_room_num = new AtomicInteger();
	    }
	
	public static StudyRoom createRoom() {
		int roomNum = seq_room_num.incrementAndGet();
		String name = "채팅방"+roomNum;
		StudyRoom room = new StudyRoom(roomNum,name);
		roomList.add(room);
		System.out.println(roomNum+"번 room 생성 완료");
		return room;
	}
	
	public static StudyRoom createRoom(StudyUser owner) {
		int roomNum = seq_room_num.incrementAndGet();
		String name = "채팅방"+roomNum;
		StudyRoom room = new StudyRoom(roomNum,name);
		room.enterUser(owner);
		room.setRoomOwner(owner);
		
		roomList.add(room);
		System.out.println(roomNum+"번 room 생성 완료");
		return room;
	}
	
	public static StudyRoom getRoom(StudyRoom room) {
		int idx = roomList.indexOf(room);
		if(idx > 0) {
			return roomList.get(idx);
		}else {
			return null;
		}
	}
	
	public static void removeRoom(StudyRoom room) {
		room.close();
		int idx = room.getIdx();
		roomList.remove(room);
		System.out.println(idx+"번 room 제거");
	}
	
	public static int roomCount() {
		return roomList.size();
	}
}
