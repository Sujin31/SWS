package member.data;

import java.sql.Date;

public class BoardDTO {
	private int idx;
	private String menu_fk;
	private String id;
	private String title;
	private String content;
	private String isfile;
	private String isreply;
	private String views;
	private Date regidate;
	private Date editdate;
	private int answers;
	
	private String menu;
	private String menu2;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getMenu_fk() {
		return menu_fk;
	}
	public void setMenu_fk(String menu_fk) {
		this.menu_fk = menu_fk;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsfile() {
		return isfile;
	}
	public void setIsfile(String isfile) {
		this.isfile = isfile;
	}
	public String getIsreply() {
		return isreply;
	}
	public void setIsreply(String isreply) {
		this.isreply = isreply;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
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
	
	public int getAnswers() {
		return answers;
	}
	public void setAnswers(int answers) {
		this.answers = answers;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getMenu2() {
		return menu2;
	}
	public void setMenu2(String menu2) {
		this.menu2 = menu2;
	}
	
	
}
