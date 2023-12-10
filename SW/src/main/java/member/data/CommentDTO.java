package member.data;

import java.sql.Date;

public class CommentDTO {
	private int idx;
	private int board_fk;
	private String id;
	private String comments;
	private Date regidate;
	private Date editdate;
	private int likes;
	private String blur;
	private String isanswer;
	private int iscomment;
	
	/*대댓글용*/
	private int idx2;
	private int board_fk2;
	private String id2;
	private String comments2;
	private Date regidate2;
	private Date editdate2;
	private int likes2;
	private String blur2;
	private String isanswer2;
	private int iscomment2;
	
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	public String getIsanswer() {
		return isanswer;
	}
	public void setIsanswer(String isanswer) {
		this.isanswer = isanswer;
	}
	public int getIscomment() {
		return iscomment;
	}
	public void setIscomment(int iscomment) {
		this.iscomment = iscomment;
	}
	
	/*대댓글용*/
	public int getIdx2() {
		return idx2;
	}
	public void setIdx2(int idx2) {
		this.idx2 = idx2;
	}
	public int getBoard_fk2() {
		return board_fk2;
	}
	public void setBoard_fk2(int board_fk2) {
		this.board_fk2 = board_fk2;
	}
	public String getId2() {
		return id2;
	}
	public void setId2(String id2) {
		this.id2 = id2;
	}
	public String getComments2() {
		return comments2;
	}
	public void setComments2(String comments2) {
		this.comments2 = comments2;
	}
	public Date getRegidate2() {
		return regidate2;
	}
	public void setRegidate2(Date regidate2) {
		this.regidate2 = regidate2;
	}
	public Date getEditdate2() {
		return editdate2;
	}
	public void setEditdate2(Date editdate2) {
		this.editdate2 = editdate2;
	}
	public int getLikes2() {
		return likes2;
	}
	public void setLikes2(int likes2) {
		this.likes2 = likes2;
	}
	public String getBlur2() {
		return blur2;
	}
	public void setBlur2(String blur2) {
		this.blur2 = blur2;
	}
	public String getIsanswer2() {
		return isanswer2;
	}
	public void setIsanswer2(String isanswer2) {
		this.isanswer2 = isanswer2;
	}
	public int getIscomment2() {
		return iscomment2;
	}
	public void setIscomment2(int iscomment2) {
		this.iscomment2 = iscomment2;
	}
	
}
