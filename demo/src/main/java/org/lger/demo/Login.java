package org.lger.demo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Login extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String realPassWord = Inquire.getPasswd(username);
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (realPassWord == null || !realPassWord.equals(password)) {
            out.println("Error");
        } else {
            out.println("Welcome");
        }
        out.close();
    }
}
