package member.data;

import java.sql.Date;

public class AnswerDTO {
	private int idx;
	private int board_fk;
	private String id;
	private String answer;
	private Date regidate;
	private Date editdate;
	private int likes;
	private String blur;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getBoard_fk() {
		return board_fk;
	}
	public void setBoard_fk(int board_fk) {
		this.board_fk = board_fk;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Date getRegidate() {
		return regidate;
	}
	public void setRegidate(Date regidate) {
		this.regidate = regidate;
	}
	public Date getEditdate() {
		return editdate;
	}
	public void setEditdate(Date editdate) {
		this.editdate = editdate;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getBlur() {
		return blur;
	}
	public void setBlur(String blur) {
		this.blur = blur;
	}
	
}
