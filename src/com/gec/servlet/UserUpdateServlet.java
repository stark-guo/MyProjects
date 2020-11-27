package com.gec.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gec.bean.Dept;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.DeptService;
import com.gec.service.UserService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.service.impl.UserServiceImpl;

/**
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet(urlPatterns = {"/useradd.action","/deptaddDept","/jobaddJob"})
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		 if("useradd.action".equals(uri)) {
			 HttpSession ss=request.getSession(false);
			 User us = (User)ss.getAttribute("user_session");
			 if(!us.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("userlist.action").forward(request, response);
			 }else {
				 response.sendRedirect("WEB-INF/jsp/user/useradd.jsp");
//			request.getRequestDispatcher("WEB-INF/jsp/user/useradd.jsp").forward(request, response);
			 }
		}else if("deptaddDept".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("deptselectDept").forward(request, response);
			 }else {
				 request.getRequestDispatcher("WEB-INF/jsp/dept/deptadd.jsp").forward(request, response);
			 }
			
			
		}else if("jobaddJob".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("jobselectJob").forward(request, response);
			 }else {
				 request.getRequestDispatcher("WEB-INF/jsp/job/jobadd.jsp").forward(request, response);
			 }
			
//			response.sendRedirect("");
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
