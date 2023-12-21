package member.studychat;

import java.util.ArrayList;
import java.util.List;

public class StudyRoom {
	private int idx;
	private String roomName;
	private StudyUser roomOwner;
	private List<StudyUser> userList;
	
	
	public StudyRoom() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * 방 생성
	 * @param idx 방번호, name 방 이름
	 * */
	public StudyRoom(int idx, String name) {
		this.idx = idx;
		this.roomName = name;
		userList = new ArrayList<StudyUser>();
	}
	
	/*
	 * 방 입장
	 * @param user
	 * */
	public void enterUser(StudyUser user) {
		user.enterRoom(this);
		userList.add(user);
	}
	
	/*
	 * 해당 유저를 방에서 내보냄
	 * @param user 내보낼 유저
	 * */
	public void exitUser(StudyUser user) {
		user.exitRoom(this);
		userList.remove(user); //해당 유저 방에서 내보냄
		
		if(userList.size() < 1) { //만약 방에 유저가 없다면
			RoomManager.removeRoom(this);
			return;
		}
		
		if(userList.size() < 2) { //만약 방에 유저가 1명이라면
			this.roomOwner = userList.get(0); // 그 유저가 방장이 됨
			return;
		}
	}
	
	/*
	 * 해당 룸의 유저를 다 퇴장시키고 삭제함
	 * */
	public void close() {
		for(StudyUser user : userList) {
			user.exitRoom(this);
		}
		this.userList.clear();
		this.userList = null;
	}
	
	 /**
     * 해당 byte 배열을 방의 모든 유저에게 전송
     * @param data 보낼 data
     */
    public void broadcast(byte[] data) {
        for (StudyUser user : userList) { // 방에 속한 유저의 수만큼 반복
            // 각 유저에게 데이터를 전송하는 메서드 호출~
            // ex) user.SendData(data);

//			try {
//				user.sock.getOutputStream().write(data); // 이런식으로 바이트배열을 보낸다.
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }
    }
    
    /*
     * 
     * */
    public StudyUser getUser(StudyUser user) {
    	int idx = userList.indexOf(user);
    	if(idx > 0) {
    		return userList.get(idx);
    	}else {
    		return null;
    	}
    }
    
    public int getUserSize() {
    	return userList.size();
    }
	

	/*get set*/
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public StudyUser getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(StudyUser roomOwner) {
		this.roomOwner = roomOwner;
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}
	/*get set 끝*/
	
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        StudyRoom Room = (StudyRoom) o;

	        return idx == Room.idx;
	    }

    @Override
    public int hashCode() {
        return idx;
    }
}
