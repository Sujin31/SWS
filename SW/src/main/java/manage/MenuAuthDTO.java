package manage;

public class MenuAuthDTO {
	private String auth_level_fk;
	private String menu_fk;
	private String mread;
	private String mwrite;
	private String mcomment;
	private String answer;
	
	//db join
	private String name;
	private String ismng;
	
	public String getAuth_level_fk() {
		return auth_level_fk;
	}
	public void setAuth_level_fk(String auth_level_fk) {
		this.auth_level_fk = auth_level_fk;
	}
	public String getMenu_fk() {
		return menu_fk;
	}
	public void setMenu_fk(String menu_fk) {
		this.menu_fk = menu_fk;
	}
	public String getMread() {
		return mread;
	}
	public void setMread(String mread) {
		this.mread = mread;
	}
	public String getMwrite() {
		return mwrite;
	}
	public void setMwrite(String mwrite) {
		this.mwrite = mwrite;
	}
	public String getMcomment() {
		return mcomment;
	}
	public void setMcomment(String mcomment) {
		this.mcomment = mcomment;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsmng() {
		return ismng;
	}
	public void setIsmng(String ismng) {
		this.ismng = ismng;
	}
	
}
