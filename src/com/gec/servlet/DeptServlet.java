package com.gec.servlet;

import java.io.IOException;
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
import com.gec.service.impl.DeptServiceImpl;

/**
 * Servlet implementation class DeptServlet
 */
@WebServlet(urlPatterns = {"/deptselectDept","/deptlist.action","/deptdel.action"})
public class DeptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dept de=new Dept();
		DeptService ds=new DeptServiceImpl();
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("deptselectDept".equals(uri)) {
			System.out.println("1111111111111111111");
			int pageNow = 1;
			 PageBean<Dept> findPage = ds.findPage(pageNow, de);
			 System.out.println(findPage);
			 List<Dept> list = findPage.getList();
//		
			 request.setAttribute("deptlist", list);
			 request.setAttribute("pageModel", findPage);
//	
			 request.getRequestDispatcher("WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
		
	}else if("deptlist.action".equals(uri)){
		int pageNow = 1;
		String page = request.getParameter("pageNow");
		String name = request.getParameter("name");
		de.setName(name);
		
		
		System.out.println(page);
		if(page!=null){
		pageNow = Integer.parseInt(page);
			
		}
		System.out.println(pageNow);
		 PageBean<Dept> findPage = ds.findPage(pageNow, de);
		 System.out.println(findPage);
		 List<Dept> list = findPage.getList();
//	
		 request.setAttribute("deptlist", list);
		 request.setAttribute("pageModel", findPage);
//
		 request.getRequestDispatcher("WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
	}else if("deptdel.action".equals(uri)) {
		HttpSession ss=request.getSession(false);
		 User uss = (User)ss.getAttribute("user_session");
		 if(!uss.getLoginname().equals("admin")) {
			 
			 request.getRequestDispatcher("deptselectDept").forward(request, response);
		 }else {
		String[] id = request.getParameterValues("deptIds");
		System.out.println(id[0]);
		int [] in=new int[id.length];
		for(int i=0;i<in.length;i++) {
			in[i]=Integer.parseInt(id[i]);
		}
		boolean delete = ds.delete(in);
		if(delete) {
			response.sendRedirect("deptlist.action");
		}else {
			response.sendRedirect("deptlist.action");
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
