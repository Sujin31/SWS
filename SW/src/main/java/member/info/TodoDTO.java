package member.info;

public class TodoDTO {
	private int idx;
	private String id;
	private String todo_date;
	private String content;
	private String isdone;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTodo_date() {
		return todo_date;
	}
	public void setTodo_date(String todo_date) {
		this.todo_date = todo_date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsdone() {
		return isdone;
	}
	public void setIsdone(String isdone) {
		this.isdone = isdone;
	}
	
	
}
