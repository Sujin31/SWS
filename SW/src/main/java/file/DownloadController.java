package file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/download")
public class DownloadController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oname = req.getParameter("oname");
		String sname = req.getParameter("sname");
		int idx = Integer.parseInt(req.getParameter("idx"));
		
		FIleUtil.download(req, resp, "/member/Uploads", oname, sname);
		FileDAO dao = new FileDAO();
		dao.downCountPlus(idx);
		dao.close();
	}
}
