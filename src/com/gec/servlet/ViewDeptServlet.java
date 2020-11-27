package com.gec.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gec.bean.Dept;
import com.gec.bean.User;
import com.gec.service.DeptService;
import com.gec.service.impl.DeptServiceImpl;

/**
 * Servlet implementation class ViewDeptServlet
 */
@WebServlet(urlPatterns = {"/viewDept.action","/saveOrUpdate.action","/saveOradd.action"})
public class ViewDeptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDeptServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dept dt=new Dept();
		DeptService ds=new DeptServiceImpl();
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("viewDept.action".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("deptselectDept").forward(request, response);
			 }else {
			String parameter = request.getParameter("id");
			if(parameter!="") {
				int id = Integer.parseInt(parameter);
				Dept findById = ds.findById(id);
				request.setAttribute("dept", findById);
				request.getRequestDispatcher("WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
			}
			 }
			
		}else if("saveOrUpdate.action".equals(uri)) {
			String parameter = request.getParameter("id");
			if(parameter!="") {
				int id = Integer.parseInt(parameter);
				dt.setId(id);
				}
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			dt.setName(name);
			dt.setRemark(remark);
			ds.update(dt);
			response.sendRedirect("deptlist.action");
		
		}else if("saveOradd.action".equals(uri)) {
			
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			dt.setName(name);
			dt.setRemark(remark);
			System.out.println(name);
			boolean save = ds.save(dt);
			if(save) {
				response.sendRedirect("deptlist.action");
			}else {
				response.sendRedirect("WEB-INF/jsp/user/useradd.jsp");
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
