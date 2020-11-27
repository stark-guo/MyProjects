package com.gec.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gec.bean.User;
import com.gec.service.UserService;
import com.gec.service.impl.UserServiceImpl;

/**
 * Servlet implementation class ViewServlet
 */
@WebServlet(urlPatterns = {"/viewUser.action","/userupdata.action","/userdel.action"})
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
User user=new User();
		
		UserService us=new UserServiceImpl();
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("viewUser.action".equals(uri)) {
			 HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("userlist.action").forward(request, response);
			 }else {
			String parameter = request.getParameter("id");
			if(parameter!="") {
				int id =Integer.parseInt(parameter) ;
				System.out.println(id);
			User findById = us.findById(id);
				request.setAttribute("user", findById);
				request.getRequestDispatcher("WEB-INF/jsp/user/useredit.jsp").forward(request, response);
							}
			
			 }	
		}else if("userupdata.action".equals(uri)) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String parameter = request.getParameter("status");
			if(parameter!="") {
				int status = Integer.parseInt(parameter);
				user.setStatus(status);
			}
			
			String loginname = request.getParameter("loginname");
			String idd=request.getParameter("id");
			System.out.println(idd);
			if(idd!="") {
				int id = Integer.parseInt(idd);
				user.setId(id);
				
			}
			
			user.setLoginname(loginname);
			user.setPassword(password);
			user.setUsername(username);
			boolean update = us.update(user);
			if(update) {
				response.sendRedirect("userlist.action");
//				request.getRequestDispatcher("WEB-INF/jsp/user/userlist.jsp").forward(request, response);
			}else {
//				request.getRequestDispatcher("userupdata.action").forward(request, response);
			}
			
			
			
		}else if("userdel.action".equals(uri)) {
			 HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("userlist.action").forward(request, response);
			 }else {
			
			
			String[] power=request.getParameterValues("userIds");
			if(power.length>0&&power!=null) {
				 int in[] = new int[power.length];
				for (int i=0;i<in.length;i++) {
					in[i]=Integer.parseInt(power[i]);
				}
				boolean delete = us.delete(in);
				if(delete) {
					request.getRequestDispatcher("userlist.action").forward(request, response);
				}
			}
			
			 }
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
