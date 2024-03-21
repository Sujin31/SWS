package member.studychat.data;

public class ChatRoomDTO {
	private int id;
	private String name;
	private String pass;
	private String fcate;
	private String scate;
	private int participant;
	private String owner;
	
	public ChatRoomDTO() {}
	
	//방 번호 없이 생성
	public ChatRoomDTO(String name, String pass, String fcate, String scate, int participant, String owner) {
		this.name = name;
		this.pass = pass;
		this.fcate = fcate;
		this.scate = scate;
		this.participant = participant;
		this.owner = owner;
	}

	//방 번호 포함 전체 생성
	public ChatRoomDTO(int id, String name, String pass, String fcate, String scate, int participant, String owner) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.fcate = fcate;
		this.scate = scate;
		this.participant = participant;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFcate() {
		return fcate;
	}

	public void setFcate(String fcate) {
		this.fcate = fcate;
	}

	public String getScate() {
		return scate;
	}

	public void setScate(String scate) {
		this.scate = scate;
	}

	public int getParticipant() {
		return participant;
	}

	public void setParticipant(int participant) {
		this.participant = participant;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
	
}
