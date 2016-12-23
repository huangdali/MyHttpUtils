package com.hdl.myhttputils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UpLoadServlet extends HttpServlet {
	private static final long serialVersionUID = -8705046949443366079L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("请求了。。。。。。。。");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
//      String userId = request.getParameter("userid");
        // 创建文件项目工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置文件上传路径
        File uploadDir = new File(this.getServletContext().getRealPath(
                "/upload/"));// 设置文件上传的路径为项目名/upload/userid/
        if (!uploadDir.exists()) {// 如果改文件夹不存在就创建
            uploadDir.mkdirs();
        }
        // 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
        String temp = System.getProperty("java.io.tmpdir");
        // 设置缓冲区大小为 5M
        factory.setSizeThreshold(1024 * 1024 * 5);
        // 设置临时文件夹为temp
        factory.setRepository(new File(temp));
        // 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        try {
            List<FileItem> list = servletFileUpload.parseRequest(request);
            for (FileItem fileItem : list) {
                File file = new File(uploadDir, fileItem.getName());
                if (!file.exists()) {// 文件不存在才创建
                    fileItem.write(file);// 保存文件
                }
            }
            pw.write("{\"message\":\"上传成功\"}");
            System.out.println("{\"message\":\"上传成功\"}");
        } catch (Exception e) {
            pw.write("{\"message\":\"上传失败\"}");
            System.out.println("{\"message\":\"上传失败\"}");
        }
    }
}
