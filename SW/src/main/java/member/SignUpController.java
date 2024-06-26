package member;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JSFunction;
import common.LoggingDB;

@WebServlet("/member/signUp")
public class SignUpController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuthDAO dao = new AuthDAO();
		List<AuthLevelDTO> list = dao.getAuthLevelsOfMember();
		dao.close();
		req.setAttribute("level", list);
		
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
			String level = req.getParameter("level");
			String birth = req.getParameter("birth");
			String gender = req.getParameter("gender");
			String phone = req.getParameter("phone");
			
			memberDTO dto = new memberDTO();
			dto.setId(id);
			dto.setName(name);
			dto.setPassword(pw);
			dto.setAuth_level_fk(level);
			dto.setBirth(birth);
			dto.setGender(gender);
			dto.setPhone(phone);
			
			memberDAO dao = new memberDAO();
			
			if(!dao.checkPhoneNum(phone)) {
				JSFunction.alertLocation(resp, "중복된 연락처가 있습니다.", "./signUp");
			}else {
				
				int result = dao.signUp(dto);
				dao.close();
				
				//log
				LoggingDB logDB = new LoggingDB();
				
				if(result == 1) {
					logDB.log(req.getSession(),"signUp","success");
					JSFunction.alertLocation(resp, "회원가입이 완료되었습니다.", "./login");
				}else {
					logDB.log(req.getSession(),"signUp","fail");
					JSFunction.alertLocation(resp, "회원가입 실패", "./login");
				}
			}
			dao.close();
		}
		
		
	}

}
