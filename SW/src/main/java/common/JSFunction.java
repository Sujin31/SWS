package common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class JSFunction {
	public static void alertLocation(HttpServletResponse resp, String msg, String url) {
		try {
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			String script = "<script> alert('" + msg + "'); location.href='"+ url +"'; </script>";
			writer.print(script);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void alertBack(HttpServletResponse resp, String msg) {
		try {
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			String script = "<script> alert('" + msg + "'); history.back(); </script>";
			writer.print(script);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
