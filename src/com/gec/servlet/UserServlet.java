package com.gec.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.UserService;
import com.gec.service.impl.UserServiceImpl;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(urlPatterns = {"/userlist.action","/usersave.action","/userlistby.action","/checkNameServlet"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user=new User();
		
	UserService us=new UserServiceImpl();
	String uri = request.getRequestURI();
	System.out.println(uri);
	uri = uri.substring(uri.lastIndexOf("/")+1);
	if("userlist.action".equals(uri)) {
		System.out.println("1111111111111111111");
		int pageNow = 1;
		
//		 String text = request.getParameter("text");
//		  String sid = request.getParameter("sid");
//		  int a=0;
//		  if(sid!=null) {
//			  a=Integer.parseInt(sid);
//		  }
//		 stu.setGrade(new Grade(a));
//		 stu.setEmail(text);
//		 stu.setName(text);
//		 System.out.println(stu.getGrade().getGrade_id());
		//如果是第二次进入则获取到pageNow
		String page = request.getParameter("pageNow");
		if(page!=null){
			pageNow = Integer.parseInt(page);
		}
		//第一次进入,请求数据
//		PageBean<Student> pb = sd.findPage(pageNow,stu);
//		List<Student> list2 = pb.getList();
//		PrintWriter out =response.getWriter();
//		ObjectMapper mapper=new ObjectMapper();
//		String json = mapper.writeValueAsString(list2);
//		out.print(json);
//		System.out.println(json);
//		request.setAttribute("list", list);
//		request.setAttribute("pb", pb);
		
		
//		System.out.println(status);
//		System.out.println(username);
//		System.out.println(loginname);
////		user.setStatus(status);
//		user.setUsername(username);
//		user.setLoginname(loginname);
//		
	
		
		
		
		 PageBean<User> findPage = us.findPage(pageNow, user);
		 System.out.println(findPage);
		 List<User> list = findPage.getList();
//		 for (User user2 : list) {
//			System.out.println(user2.toString());
//		}
		 request.setAttribute("user", list);
		 request.setAttribute("pageModel", findPage);
//		HttpSession seesion=request.getSession();
//		seesion.setAttribute("user", findAll);
//		 response.sendRedirect("WEB-INF/jsp/user/s.jsp");
		request.getRequestDispatcher("WEB-INF/jsp/user/userlist.jsp").forward(request, response);
//		request.getRequestDispatcher("WEB-INF/jsp/user/s.jsp");
	}else if("userlistby.action".equals(uri)){
		
		String loginname = request.getParameter("loginname");
		if(loginname!=null) {
			user.setLoginname(loginname);
		}
		
		String username = request.getParameter("username");
		if(username!=null) {
			user.setUsername(username);
		}
		String s = request.getParameter("status");
		if(s!="") {
		int status=Integer.parseInt(s);
	if(status>0) {
			user.setStatus(status);
		}
		}
		int pageNow = 1;
		
//		
		String page = request.getParameter("pageNow");
		if(page!=null){
			pageNow = Integer.parseInt(page);
		}
		
	
		
		
		
		 PageBean<User> findPage = us.findPage(pageNow, user);
		 System.out.println(findPage);
		 List<User> list = findPage.getList();
//		 for (User user2 : list) {
//			System.out.println(user2.toString());
//		}
		 request.setAttribute("user", list);
		 request.setAttribute("pageModel", findPage);
//	
		request.getRequestDispatcher("WEB-INF/jsp/user/userlist.jsp").forward(request, response);
//		
	}else if("usersave.action".equals(uri)) {
		String username = request.getParameter("username");
		String s = request.getParameter("status");
		if(s!="") {
			int status = Integer.parseInt(s);
			user.setStatus(status);
		}
		Date dd=new Date();
	
		//格式化
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sim.format(dd);
		SimpleDateFormat si=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now;
		try {
			now = si.parse(time);
			user.setCreatedate(now);
//			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");
		System.out.println(username);
		user.setUsername(username);
		user.setLoginname(loginname);
		user.setPassword(password);
		boolean save = us.save(user);
		if(save) {
			request.getRequestDispatcher("userlist.action").forward(request, response);
		}else {
			request.getRequestDispatcher("useradd.action").forward(request, response);
		}
		
		
		
	
	
		
	}else if("checkNameServlet".equals(uri)) {
		String name=request.getParameter("loginname");
		PrintWriter out=response.getWriter();
		System.out.println(name);
		int find = find(name);
		if(find==1) {
			out.print("登录名不可用");
		}else {
			out.print("");
		}
				
	
		
	}
	
	
	}
	public int find(String loginname) {
		UserService us=new UserServiceImpl();
		List<User> findAll = us.findAll();
		for (User user : findAll) {
			if(user.getLoginname().equals(loginname)) {
				return 1;
			}
		}
		return 0;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
