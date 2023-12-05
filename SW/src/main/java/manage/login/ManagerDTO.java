package manage.login;

public class ManagerDTO {
	private String id;
	private String auth_level_fk;
	private String password;
	private String name;
	private String phone;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuth_level_fk() {
		return auth_level_fk;
	}
	public void setAuth_level_fk(String auth_level_fk) {
		this.auth_level_fk = auth_level_fk;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
