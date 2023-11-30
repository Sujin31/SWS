package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class FIleUtil{
	public static MultipartRequest uploadFile(HttpServletRequest req, String saveDir, int maxPostSize) {
		try {
			return new MultipartRequest(req, saveDir,maxPostSize,"UTF-8");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void download(HttpServletRequest req, HttpServletResponse resp, String dir, String oname, String sname) {
		String sdir = req.getServletContext().getRealPath(dir);
		
		try {
			//파일 입력 스트림 생성
			File file = new File(sdir,sname);
			InputStream iStream = new FileInputStream(file);
			
			//한글 깨짐 방지
			String client = req.getHeader("User-Agent");
			if(client.indexOf("WOW64") == -1) {
				oname = new String(oname.getBytes("UTF-8"), "ISO-8859-1");
			}else {
				oname = new String(oname.getBytes("KSC5601"), "ISO-8859-1");
			}
			
			resp.reset();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\""+ oname + "\"");
			resp.setHeader("Content-Length", ""+file.length());
			
			OutputStream oStream = resp.getOutputStream();
			
			byte b[] = new byte[(int)file.length()];
			int readBuffer = 0;
			while((readBuffer = iStream.read(b)) > 0 ) {
				oStream.write(b,0,readBuffer);
			}
			
			iStream.close();
			oStream.close();
			
		}catch (FileNotFoundException e) {
			System.out.println("파일을 찾을 수 없습니다.");
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("파일다운로드 오류");
			e.printStackTrace();
		}
		
	}
	
	public static void deleteFile(HttpServletRequest req, String dir, String sname) {
		String sdir = req.getServletContext().getRealPath(dir);
		File file = new File(sdir+File.separator+sname);
		if(file.exists()) {
			file.delete();
		}
	}
}
