package com.journaldev.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/DownloadImage")
public class DownloadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;
	@Override
	public void init() throws ServletException{
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("do get method");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.write("<html><head></head><body>");
		try {
			String recipient = request.getParameter("recipient");
			String dropDown = request.getParameter("dropdown");
			
//			String url = request.getServletContext().getAttribute("FILES_DIR")+"\\eid.jpg";
			String url = getServletContext().getRealPath("img//eid.jpg");
	        byte[] b = mergeImageAndText(url, dropDown +"/"+recipient, new Point(1000,1000));
	        File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+"SAVC_Greeting"+UUID.randomUUID()+".jpeg");
	        FileOutputStream fos = new FileOutputStream(file);
	       /* out.write("<div id=\"download\">File "+file.getName()+ " uploaded successfully.");*/
//	        out.write("<br><br>");
//			out.write("<a href=\"DownloadImage?fileName="+file.getName()+"\">تحميل </a>");
	        fos.write(b);
	        fos.close();
	        downloadFile(request, response, file.getName());
//	        request.getRequestDispatcher("EmailForm.jsp").include(request, response);
			
		} catch (Exception e) {
//			out.write("Exception in uploading file.");
		}
//		out.write("</div></body></html>");
	}
    private void downloadFile(HttpServletRequest request,HttpServletResponse response,String fileName) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		if(fileName == null || fileName.equals("")){
			throw new ServletException("File Name can't be null or empty");
		}
		File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+fileName);
		if(!file.exists()){
			throw new ServletException("File doesn't exists on server.");
		}
		System.out.println("File location on server::"+file.getAbsolutePath());
		ServletContext ctx = getServletContext();
		InputStream fis = new FileInputStream(file);
		String mimeType = ctx.getMimeType(file.getAbsolutePath());
		response.setContentType(mimeType != null? mimeType:"application/octet-stream");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		ServletOutputStream os  = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read=0;
		while((read = fis.read(bufferData))!= -1){
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
		System.out.println("File downloaded at client successfully");
		
	}
	public byte[] mergeImageAndText(String imageFilePath,
            String text, Point textPosition) throws IOException, FontFormatException {
        BufferedImage im = ImageIO.read(new File(imageFilePath));
        Graphics2D g2 = im.createGraphics();
        g2.setColor(new Color (46, 59, 111));
        String url = getServletContext().getRealPath("font//Calibri400.ttf");
        Font f = Font.createFont(Font.TRUETYPE_FONT, new File(url));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(f);
        Font font = new Font("calibri", Font.BOLD, 45);
        
        g2.setFont(font); 
        g2.drawString(text, 75 , 750);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(im, "jpeg", baos);
        return baos.toByteArray();
    }
}
