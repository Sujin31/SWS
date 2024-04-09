package common;

import javax.servlet.http.HttpSession;

public class LoggingDB extends DBConnPool{
	
	/*
	public void log(String ip, String id, String methodName, String desc) {
		history(ip,id,methodName,desc);
		System.out.println(methodName+" => "+desc);
	}
	
	//아이디 없음
	public void log(String ip, String methodName, String desc) {
		histroy(ip,methodName,desc);
		System.out.println(methodName+" => "+desc);
	}
	*/
	
	public void log(HttpSession session, String m,String d) {
		String ip = (String)session.getAttribute("ip");
		String id = (String)session.getAttribute("UserId");
		history(ip,id,m,d);
		System.out.println(id+" => "+m+"_"+d);
	}
	
	/*
	private void histroy(String ip, String methodName, String desc) {
		String query = "insert into log_history (ip,action_code,description) VALUES(?,?,?)";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, ip);
			psmt.setString(2, methodName);
			psmt.setString(3, desc);
			
			psmt.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	private void history(String ip, String id, String methodName, String desc) {
		String query = "insert into log_history (ip,id,action_code,description) VALUES(?,?,?,?)";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, ip);
			psmt.setString(2, id);
			psmt.setString(3, methodName);
			psmt.setString(4, desc);
			
			psmt.execute();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
