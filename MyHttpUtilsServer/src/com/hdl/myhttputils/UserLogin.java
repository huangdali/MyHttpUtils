package com.hdl.myhttputils;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class UserLogin extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        System.out.println("---------" + username);
        String pwd = request.getParameter("pwd");
        String msg = "";
        if ("admin".equals(username) && "132".equals(pwd)) {
            msg = "登录成功";
        } else {
            msg = "用户名或密码错误";
        }
        msg=URLDecoder.decode(msg,"UTF-8");
        String json = "{\"response\":\"succeed\",\"msg\":\"" + msg + "\"}";
        response.getWriter().write(json);
    }
}
