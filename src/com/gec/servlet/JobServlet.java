package com.gec.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Dept;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.dao.JobDao;
import com.gec.dao.impl.JobDaoImpl;

/**
 * Servlet implementation class JobServlet
 */
@WebServlet(urlPatterns = {"/jobselectJob","/joblist.action"})
public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JobDao jd=new JobDaoImpl();
		Job jb=new Job();
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("jobselectJob".equals(uri)) {
			System.out.println("1111111111111111111");
			int pageNow = 1;
			 PageBean<Job> findPage = jd.findPage(pageNow, jb);
			 System.out.println(findPage);
			 List<Job> list = findPage.getList();
			 for (Job job : list) {
				System.out.println(job.toString());
			}
////		
			 request.setAttribute("joblist", list);
			 request.setAttribute("pageModel", findPage);
//	
			 request.getRequestDispatcher("WEB-INF/jsp/job/joblist.jsp").forward(request, response);
		}else if("joblist.action".equals(uri)) {
			System.out.println("1111111111111111111");
			int pageNow = 1;
			String parameter = request.getParameter("pageNow");
			if(parameter!=null) {
				pageNow=Integer.parseInt(parameter);
			}
			String name = request.getParameter("name");
			jb.setName(name);
			 PageBean<Job> findPage = jd.findPage(pageNow, jb);
			 System.out.println(findPage);
			 List<Job> list = findPage.getList();
////		
			 request.setAttribute("joblist", list);
			 request.setAttribute("pageModel", findPage);
//	
			 request.getRequestDispatcher("WEB-INF/jsp/job/joblist.jsp").forward(request, response);
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
