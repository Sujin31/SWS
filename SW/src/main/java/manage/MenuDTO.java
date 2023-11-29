package manage;

public class MenuDTO {
	private String code;
	private String pmenu_code;
	private int depts;
	private String name;
	private String link;
	private String board_tmp;
	private int list_order;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPmenu_code() {
		return pmenu_code;
	}
	public void setPmenu_code(String pmenu_code) {
		this.pmenu_code = pmenu_code;
	}
	public int getDepts() {
		return depts;
	}
	public void setDepts(int depts) {
		this.depts = depts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getBoard_tmp() {
		return board_tmp;
	}
	public void setBoard_tmp(String board_tmp) {
		this.board_tmp = board_tmp;
	}
	public int getList_order() {
		return list_order;
	}
	public void setList_order(int list_order) {
		this.list_order = list_order;
	}
}
