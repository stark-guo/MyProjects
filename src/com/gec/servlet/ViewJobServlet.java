package com.gec.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gec.bean.Job;
import com.gec.bean.User;
import com.gec.service.JobService;
import com.gec.service.impl.JobServiceImpl;

/**
 * Servlet implementation class ViewJobServlet
 */
@WebServlet(urlPatterns = {"/viewJob.action","/jobedit.action","/jobdel.action","/jobaddsave.action"})
public class ViewJobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Job jb=new Job();
		JobService js=new JobServiceImpl();
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("viewJob.action".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("jobselectJob").forward(request, response);
			 }else {
			String parameter = request.getParameter("id");
			System.out.println(parameter);
			if(parameter!="") {
				int id=Integer.parseInt(parameter);
				Job findById = js.findById(id);
				request.setAttribute("job", findById);
				request.getRequestDispatcher("WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
			}
			 }
		}else if("jobedit.action".equals(uri)) {
			Job j=new Job();
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			String parameter = request.getParameter("id");
			if(parameter!="") {
				int id =Integer.parseInt(parameter);
				j.setId(id);
			}
			System.out.println(name);
			j.setName(name);
			j.setRemark(remark);
			 js.update(j);
			response.sendRedirect("jobselectJob");
		}else if("jobdel.action".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("jobselectJob").forward(request, response);
			 }else {
			String[] ps = request.getParameterValues("jobIds");
			int [] id=new int[ps.length];
			for(int i=0;i<id.length;i++) {
				id[i]=Integer.parseInt(ps[i]);
			}
			js.delete(id);
			response.sendRedirect("jobselectJob");
			 }
		}else if("jobaddsave.action".equals(uri)) {
			String name=request.getParameter("name");
			String remark = request.getParameter("remark");
			System.out.println(name);
			System.out.println(remark);
			jb.setName(name);
			jb.setRemark(remark);
			js.save(jb);
			response.sendRedirect("jobselectJob");
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
