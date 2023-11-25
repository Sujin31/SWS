package member;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JSFunction;

@WebServlet("/member/signUp.do")
public class SignUpController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/member/01_login/SignUp.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int mode = Integer.parseInt(req.getParameter("mode"));
		
		if(mode == 1) {//아이디 체크 ajax
			String cid = req.getParameter("check_id");
			boolean flag = false;
			memberDAO dao = new memberDAO();
			flag = dao.checkId(cid);
			dao.close();
			
			if(flag) {
				resp.getWriter().write("T");
			}else {
				resp.getWriter().write("F");
			}
		}else {
			String name = req.getParameter("name");
			String id = req.getParameter("id");
			String pw = req.getParameter("pw");
			String birth = req.getParameter("birth");
			String gender = req.getParameter("gender");
			String phone = req.getParameter("phone");
			
			memberDTO dto = new memberDTO();
			dto.setId(id);
			dto.setName(name);
			dto.setPassword(pw);
			dto.setBirth(birth);
			dto.setGender(gender);
			dto.setPhone(phone);
			
			memberDAO dao = new memberDAO();
			if(!dao.checkPhoneNum(phone)) {
				JSFunction.alertLocation(resp, "중복된 연락처가 있습니다.", "./signUp.do");
			}else {
				int result = dao.signUp(dto);
				dao.close();
				
				if(result == 1) {
					JSFunction.alertLocation(resp, "회원가입이 완료되었습니다.", "./login.do");
				}else {
					JSFunction.alertLocation(resp, "회원가입 실패", "./login.do");
				}
			}
			
		}
		
		
	}

}
