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

import com.gec.bean.Document;
import com.gec.bean.Notice;
import com.gec.bean.PageBean;
import com.gec.bean.Type;
import com.gec.bean.User;
import com.gec.service.NoticeService;
import com.gec.service.TypeService;
import com.gec.service.impl.NoticeServiceImpl;
import com.gec.service.impl.TypeServiceImpl;

/**
 * Servlet implementation class NoticeServlet
 */
@WebServlet(urlPatterns = {"/selectNotice","/noticelist.action","/addNotice","/viewNotice.action","/noticesaveOrUpdate.action","/noticedel.action"})
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Notice nt=new Notice();
		TypeService ts=new TypeServiceImpl();
		NoticeService ns=new NoticeServiceImpl();
		String uri=request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("selectNotice".equals(uri)) {
			int pageNow=1;
			PageBean<Notice> findPage = ns.findPage(pageNow, nt);
			List<Notice> list = findPage.getList();
			
			request.setAttribute("noticelist", list);
			request.setAttribute("pageModel", findPage);
			request.getRequestDispatcher("WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
		}else if("noticelist.action".equals(uri)) {
			int pageNow=1;
			String parameter = request.getParameter("pageNow");
			if(parameter!=""&&parameter!="") {
				pageNow=Integer.parseInt(parameter);
				
			}
			String name = request.getParameter("name");
			nt.setName(name);
			PageBean<Notice> findPage = ns.findPage(pageNow, nt);
			List<Notice> list = findPage.getList();
			
			request.setAttribute("noticelist", list);
			request.setAttribute("pageModel", findPage);
			request.getRequestDispatcher("WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
			
		}else if("addNotice".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("selectNotice").forward(request, response);
			 }else {
			String parameter = request.getParameter("flag");
			if(parameter.equals("1")) {
				List<Type> findAll = ts.findAll();
				for (Type type : findAll) {
					System.out.println(type.toString());
				}
				request.setAttribute("tex", "添加公告");
				request.setAttribute("val", "添加");
				request.setAttribute("types", findAll);
				request.getRequestDispatcher("WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
//				response.sendRedirect("WEB-INF/jsp/notice/notice_save_update.jsp");
			}
			 }	
		}else if("viewNotice.action".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("selectNotice").forward(request, response);
			 }else {
			String id=request.getParameter("id");
			System.out.println(id);
			if(id!="") {
				int i=Integer.parseInt(id);
				Notice findById = ns.findById(i);
				List<Type> findAll = ts.findAll();
				request.setAttribute("tex", "修改公告");
				request.setAttribute("val", "修改");
				request.setAttribute("notice", findById);
				request.setAttribute("types", findAll);
				request.getRequestDispatcher("WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
				
			}
			 }
		}else if("noticesaveOrUpdate.action".equals(uri)) {
			String val = request.getParameter("submit");
			System.out.println(val);
			if(val.equals("添加")) {
				HttpSession session=request.getSession(false);
				User user = (User)session.getAttribute("user_session");
				nt.setUser(user);
				String text=request.getParameter("text");
				nt.setContent(text);
				String name = request.getParameter("name");
				nt.setName(name);
				Date date=new Date();
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String format = sim.format(date);
				try {
					Date parse = sim.parse(format);
					nt.setCreateDate(parse);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String typeid = request.getParameter("type_id");
				if(typeid!="") {
					int type_id=Integer.parseInt(typeid);
					nt.setType(new Type(type_id));
				}
				ns.save(nt);
				response.sendRedirect("selectNotice");
			}else {
				String parameter = request.getParameter("id");
				if(parameter!="") {
					int id=Integer.parseInt(parameter);
					nt.setId(id);
				}
				String text=request.getParameter("text");
				nt.setContent(text);
				String name = request.getParameter("name");
				nt.setName(name);
				Date date=new Date();
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String format = sim.format(date);
				try {
					Date parse = sim.parse(format);
					nt.setModifyDate(parse);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String typeid = request.getParameter("type_id");
				if(typeid!="") {
					int type_id=Integer.parseInt(typeid);
					nt.setType(new Type(type_id));
				}
				ns.update(nt);
				response.sendRedirect("selectNotice");
			}
			
		}else if("noticedel.action".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("selectNotice").forward(request, response);
			 }else {
			String []pt = request.getParameterValues("noticeIds");
			int [] id=new int[pt.length];
			for (int i = 0; i < id.length; i++) {
				id[i]=Integer.parseInt(pt[i]);
				System.out.println(id[i]);
			}
			ns.delete(id);
			response.sendRedirect("selectNotice");
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
