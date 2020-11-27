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

import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.DeptService;
import com.gec.service.EmployeeService;
import com.gec.service.JobService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.service.impl.EmployeeServiceImpl;
import com.gec.service.impl.JobServiceImpl;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet(urlPatterns = {"/selectemployeelist.action","/employeelist.action","/updateEmployee","/employeedel.action","/addEmployee","/employeeadd.action","/checkServlet"})
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DeptService ds=new DeptServiceImpl();
		JobService js=new JobServiceImpl();
		EmployeeService es=new EmployeeServiceImpl();
		Employee ee=new Employee();
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("selectemployeelist.action".equals(uri)) {
			
			int pageNow=1;
			PageBean<Employee> findPage = es.findPage(pageNow, ee);
			 System.out.println(findPage);
			 List<Employee> list = findPage.getList();
			 List<Job> findAll = js.findAll();
			 List<Dept> findAll2 = ds.findAll();
			 for (Employee em : list) {
				em.getDept().setName(ds.findById(em.getDept().getId()).getName());
				em.getJob().setName(js.findById(em.getJob().getId()).getName());
			}
			 request.setAttribute("deptList", findAll2);
			 request.setAttribute("jobList", findAll);
			 request.setAttribute("employeelist", list);
			 request.setAttribute("pageModel", findPage);
//	
			 request.getRequestDispatcher("WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
		
		}else if("employeelist.action".equals(uri)) {
			int pageNow=1;
			String parameter = request.getParameter("pageNow");
			if(parameter!=null) {
				pageNow=Integer.parseInt(parameter);
			}
			String name = request.getParameter("name");
			ee.setName(name);
			String phone = request.getParameter("phone");
			ee.setPhone(phone);
			String cardId = request.getParameter("cardId");
			ee.setCardId(cardId);
			String job_id=request.getParameter("job_id");
			if(job_id!="") {
				int id =Integer.parseInt(job_id);
				ee.setJob(new Job(id));
			}
			String sex=request.getParameter("sex");
			if(sex!="") {
				int se=Integer.parseInt(sex);
				ee.setSex(se);
			}
			String dept_id=request.getParameter("dept_id");
			if(dept_id!="") {
				int deptid=Integer.parseInt(dept_id);
				ee.setDept(new Dept(deptid));
			}
			PageBean<Employee> findPage = es.findPage(pageNow, ee);
			 System.out.println(findPage);
			 List<Employee> list = findPage.getList();
			 List<Job> findAll = js.findAll();
			 List<Dept> findAll2 = ds.findAll();
			 for (Employee em : list) {
				em.getDept().setName(ds.findById(em.getDept().getId()).getName());
				em.getJob().setName(js.findById(em.getJob().getId()).getName());
			}
			 request.setAttribute("deptList", findAll2);
			 request.setAttribute("jobList", findAll);
			 request.setAttribute("employeelist", list);
			 request.setAttribute("pageModel", findPage);
//	
			 request.getRequestDispatcher("WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
		}else if("updateEmployee".equals(uri)) {
			String flag = request.getParameter("flag");
			String parameter = request.getParameter("id");
			System.out.println(parameter);
			System.out.println(flag);
			if(flag.equals("1")) {
				HttpSession ss=request.getSession(false);
				 User uss = (User)ss.getAttribute("user_session");
				 if(!uss.getLoginname().equals("admin")) {
					 
					 request.getRequestDispatcher("selectemployeelist.action").forward(request, response);
				 }else {
				List<Dept> findAll = ds.findAll();
				List<Job> findAll2 = js.findAll();
				System.out.println("111111");
//				String parameter = request.getParameter("id");
				if(parameter!="") {
					int id=Integer.parseInt(parameter);
					Employee findById = es.findById(id);
//					System.out.println(findById.getBirthday());
//					System.out.println(findById.getAddress());
					request.setAttribute("depts", findAll);
					request.setAttribute("jobs", findAll2);
					request.setAttribute("employee", findById);
					request.getRequestDispatcher("WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
				}
				 }
			}else {
			if(parameter!="") {
				int id=Integer.parseInt(parameter);
				ee.setId(id);
				System.out.println("id"+id);
			}
				String name = request.getParameter("name");
				ee.setName(name);
				String sex = request.getParameter("sex");
				if(sex!="") {
					int se=Integer.parseInt(sex);
					ee.setSex(se);
				}
				String education = request.getParameter("education");
				ee.setEducation(education);
				String phone = request.getParameter("phone");
				ee.setPhone(phone);
				String party = request.getParameter("party");
				ee.setParty(party);
				String address = request.getParameter("address");
				ee.setAddress(address);
				String birthday = request.getParameter("birthday");
				System.out.println(birthday);
				SimpleDateFormat si=new SimpleDateFormat("yyyy-MM-dd");
				Date date;
				try {
					date = si.parse(birthday);
					ee.setBirthday(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String speciality = request.getParameter("speciality");
				ee.setSpeciality(speciality);
				String remark = request.getParameter("remark");
				ee.setRemark(remark);
				String cardId = request.getParameter("cardId");
				ee.setCardId(cardId);
				String job_id = request.getParameter("job_id");
				if(job_id!="") {
					int jobid=Integer.parseInt(job_id);
					ee.setJob(new Job(jobid));
				}
				String email = request.getParameter("email");
				ee.setEmail(email);
				String tel = request.getParameter("tel");
				ee.setTel(tel);
				String qqNum = request.getParameter("qqNum");
				ee.setQqNum(qqNum);
				String postCode = request.getParameter("postCode");
				ee.setPostCode(postCode);
				String race = request.getParameter("race");
				ee.setRace(race);
				String hobby = request.getParameter("hobby");
				ee.setHobby(hobby);
				String dept_id = request.getParameter("dept_id");
				if(dept_id!="") {
					int deptid=Integer.parseInt(dept_id);
					ee.setDept(new Dept(deptid));
				}
				es.update(ee);
				response.sendRedirect("selectemployeelist.action");
			}
		}else if("employeedel.action".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("selectemployeelist.action").forward(request, response);
			 }else {
			String[] ps = request.getParameterValues("checkbox");
			int []checkbox =new int[ps.length];
			for(int i=0;i<checkbox.length;i++) {
				checkbox[i]=Integer.parseInt(ps[i]);
			}
			es.delete(checkbox);
			 	response.sendRedirect("selectemployeelist.action");
			 }
		}else if("employeeadd.action".equals(uri)) {
			HttpSession ss=request.getSession(false);
			 User uss = (User)ss.getAttribute("user_session");
			 if(!uss.getLoginname().equals("admin")) {
				 
				 request.getRequestDispatcher("selectemployeelist.action").forward(request, response);
			 }else {
			List<Dept> findAll = ds.findAll();
			List<Job> findAll2 = js.findAll();
			request.setAttribute("deptList", findAll);
			for (Job job : findAll2) {
				System.out.println(job.toString());
			}
			request.setAttribute("jobList", findAll2);
			request.getRequestDispatcher("WEB-INF/jsp/employee/employeeadd.jsp").forward(request, response);
			 }
		} else if("addEmployee".equals(uri)) {
			String name = request.getParameter("name");
			ee.setName(name);
			String sex = request.getParameter("sex");
			if(sex!="") {
				int se=Integer.parseInt(sex);
				ee.setSex(se);
			}
			String education = request.getParameter("education");
			ee.setEducation(education);
			String phone = request.getParameter("phone");
			ee.setPhone(phone);
			String party = request.getParameter("party");
			ee.setParty(party);
			String address = request.getParameter("address");
			ee.setAddress(address);
			String birthday = request.getParameter("birthday");
			System.out.println(birthday);
			SimpleDateFormat si=new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = si.parse(birthday);
				ee.setBirthday(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String speciality = request.getParameter("speciality");
			ee.setSpeciality(speciality);
			String remark = request.getParameter("remark");
			ee.setRemark(remark);
			String cardId = request.getParameter("cardId");
			ee.setCardId(cardId);
			String job_id = request.getParameter("job_id");
			if(job_id!="") {
				int jobid=Integer.parseInt(job_id);
				
					ee.setJob(new Job(jobid));
				
				
			}
			String email = request.getParameter("email");
			ee.setEmail(email);
			String tel = request.getParameter("tel");
			ee.setTel(tel);
			String qqNum = request.getParameter("qqNum");
			ee.setQqNum(qqNum);
			String postCode = request.getParameter("postCode");
			ee.setPostCode(postCode);
			String race = request.getParameter("race");
			ee.setRace(race);
			String hobby = request.getParameter("hobby");
			ee.setHobby(hobby);
			String deptid = request.getParameter("dept_id");
			if(deptid!=""&&deptid!=null) {
				int depti=Integer.parseInt(deptid);
				System.out.println(depti);
				ee.setDept(new Dept(depti));
			}
			Date dat=new Date();
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String da=sim.format(dat);
			try {
				Date parse = sim.parse(da);
				ee.setCreateDate(parse);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			es.save(ee);
			response.sendRedirect("selectemployeelist.action");
		}else if("checkServlet".equals(uri)) {
			
			String name=request.getParameter("cardId");
			PrintWriter out=response.getWriter();
			System.out.println(name);
			int find = find(name);
			if(find==1) {
				out.print("身份证号重复");
				System.out.println("11111");
			}else {
				out.print("");
			}
				
			
		}
	}

	public int find(String name) {
		EmployeeService es=new EmployeeServiceImpl();
		List<Employee> findAll = es.findAll();
		for (Employee em : findAll) {
			if(em.getCardId().equals(name)) {
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
