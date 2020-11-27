package com.gec.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gec.bean.Document;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.DocumentService;
import com.gec.service.UserService;
import com.gec.service.impl.DocumentServiceImpl;
import com.gec.service.impl.UserServiceImpl;

/**
 * Servlet implementation class DocumentServlet
 */
@WebServlet(urlPatterns = {"/documentlist.action","/documentadd.action","/documentaddsave.action","/documentlist","/downLoad.png","/removeDocument","/updateDocument","/documentupdateDocument"})
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DocumentService ds=new DocumentServiceImpl();
		UserService us=new UserServiceImpl();
		Document dc=new Document();
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("documentlist.action".equals(uri)) {
			
			int pageNow=1;
			PageBean<Document> findPage = ds.findPage(pageNow, dc);
			List<Document> list = findPage.getList();
			for (Document dt : list) {
				dt.getUser().setUsername(us.findById(dt.getUser().getId()).getUsername());;
			}
			request.setAttribute("documentlist", list);
			request.setAttribute("pageModel", findPage);
			request.getRequestDispatcher("WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
		}else if("documentlist".equals(uri)) {
			String parameter = request.getParameter("pageNow");
			System.out.println(parameter);
			String title = request.getParameter("title");
			dc.setTitle(title);
			int pageNow=1;
			if(parameter!=""&&parameter!=null) {
				pageNow=Integer.parseInt(parameter);
			}
			PageBean<Document> findPage = ds.findPage(pageNow, dc);
			List<Document> list = findPage.getList();
			for (Document dt : list) {
				dt.getUser().setUsername(us.findById(dt.getUser().getId()).getUsername());;
			}
			request.setAttribute("documentlist", list);
			request.setAttribute("pageModel", findPage);
			request.getRequestDispatcher("WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
		}else if("documentadd.action".equals(uri)) {
			
			request.getRequestDispatcher("WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
			 
		}else if("documentaddsave.action".equals(uri)) {
			boolean flag=false;
			HttpSession session = request.getSession(false);
			User user=(User)session.getAttribute("user_session");
			dc.setUser(user);
			Date date=new Date();
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = sim.format(date);
			try {
				Date parse = sim.parse(format);
				
				user.setCreatedate(parse);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			 //是通过upload上传组件进行获取内容
			 
			//1.判断是否为二进制流提交内容
			if(ServletFileUpload.isMultipartContent(request)){
				//2.创建一个存储工厂来存储数据内容
				//DiskFileItemFactory 是一个内存数据保存工厂对象
				FileItemFactory factory = new DiskFileItemFactory();
				//3.获取到组件中servletFileUpload,将所解析的内容放入工厂,通过工厂转换为每一项文件
				ServletFileUpload upload = new ServletFileUpload(factory);
				//4.通过servletFileUpload类中的parseRequest将request中的数据转换为FileItem
				try {
					List<FileItem> list = upload.parseRequest(request);
					if(list!=null){
						//5.循环所有项,
						for (FileItem item : list) {
							//判断file是否为普通表单文件,isFormField判断 是普通表单数据则返回true 否则返回false
							if(item.isFormField()){
								if("title".equals(item.getFieldName())){
									//如果找到对应属性名,直接获取对应的值
									dc.setTitle(item.getString("utf-8"));
								}
								if("remark".equals(item.getFieldName())){
									dc.setRemark(item.getString());
								}
							}else{
								//6.获取到要存储的文件夹
								String path ="D:/第二阶段项目/WebContent/ueditor/jsp/upload/file";
								System.out.println(path);
								
								//获取文件名
								String fileName = item.getName();
								String filetype=fileName.substring(fileName.lastIndexOf(".")+1);
								System.out.println(filetype);
								if(filetype.equals("png")||filetype.equals("jpg")) {
									path="D:/第二阶段项目/WebContent/ueditor/jsp/upload/image";
								}
								File file = new File(path);
								//判断该路径是否存在,如果不存在就新建
								if(!file.exists()){
									file.mkdirs();
								}
								String fileBytes = fileName.substring(0, fileName.lastIndexOf("."))+System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
								File newFile = new File(file, fileBytes);
								//将item写出到指定文件中newFile
								item.write(newFile);
								flag = true;
								
								//将文件名保存在对象中
								dc.setFileBytes(fileBytes);
								dc.setFileName(fileName);
								dc.setFiletype(filetype);
							}
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//普通提交方式获取
//				user.setUserName(request.getParameter("username"));
//				user.setPassword(request.getParameter("pwd"));
				flag = true;
			}
//			System.out.println("用户名:====="+user.getUserName());
//			System.out.println("密码为:====="+user.getPassword());
//			System.out.println("图片名为:====="+user.getUrl());
			if(flag){
				ds.save(dc);
//				request.setAttribute("user", user);
				request.getRequestDispatcher("documentlist.action").forward(request, response);
			
			
		}
	}else if("downLoad.png".equals(uri)) {
		String parameter = request.getParameter("id");
		if(parameter!=""&&parameter!=null) {
		int	id=Integer.parseInt(parameter);
			Document findById = ds.findById(id);
			String fileBytes = findById.getFileBytes();
			String filetype = findById.getFiletype();
			//获取到服务器所在文件夹真实路径
			String path="D:/第二阶段项目/WebContent/ueditor/jsp/upload/file";
			if(filetype.equals("png")||filetype.equals("jpg")) {
				path="D:/第二阶段项目/WebContent/ueditor/jsp/upload/image";
			}
			
			
			//获取要下载的文件名称
//			String fileName=findById.getFileName();
//			System.out.println(fileName);
			//通过输入流，将文件读取
			//
			InputStream in=new FileInputStream(path+File.separator+fileBytes);
			//设置响应头的内容，设置响应类型为下载类型，并设置下载文件的名称
			response.setHeader("Content-Disposition", "attachment;filename="+fileBytes);
			//创建输出流对象，将流输出到客户端
			ServletOutputStream out=response.getOutputStream();
			//将流输出
			int len=0;
			byte[]b=new byte[1024];
			while((len=in.read(b))>0) {
				out.write(b,0,len);
				
			}
			out.flush();
			out.close();
			in.close();
		}
		}else if("removeDocument".equals(uri)) {
			String[] ps = request.getParameterValues("ids");
			String str="";
			for (String string : ps) {
				str+=string;
			}
			String[] split = str.split(",");
			int [] id=new int[split.length];
			for (int i = 0; i < id.length; i++) {
				id[i]=Integer.parseInt(split[i]);
				Document findById = ds.findById(Integer.parseInt(split[i]));
				String filetype = findById.getFiletype();
				String path="D:/第二阶段项目/WebContent/ueditor/jsp/upload/file";
				if(filetype.equals("png")||filetype.equals("jpg")) {
					path="D:/第二阶段项目/WebContent/ueditor/jsp/upload/image";
				}
			String	filePathAndName	=path+File.separator+findById.getFileBytes();
			delFile(filePathAndName);
			}
			
			ds.delete(id);
			response.sendRedirect("documentlist.action");
			System.out.println();
		}else if("updateDocument".equals(uri)) {
			String parameter = request.getParameter("id");
			System.out.println(parameter);
			if(parameter!="") {
				int id=Integer.parseInt(parameter);
				Document findById = ds.findById(id);
				request.setAttribute("document", findById);
				
			}
			request.getRequestDispatcher("WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
		}else if("documentupdateDocument".equals(uri)) {
			String parameter = request.getParameter("id");
			boolean flag=false;
		
			 //是通过upload上传组件进行获取内容
			 
			//1.判断是否为二进制流提交内容
			if(ServletFileUpload.isMultipartContent(request)){
				//2.创建一个存储工厂来存储数据内容
				//DiskFileItemFactory 是一个内存数据保存工厂对象
				FileItemFactory factory = new DiskFileItemFactory();
				//3.获取到组件中servletFileUpload,将所解析的内容放入工厂,通过工厂转换为每一项文件
				ServletFileUpload upload = new ServletFileUpload(factory);
				//4.通过servletFileUpload类中的parseRequest将request中的数据转换为FileItem
				try {
					List<FileItem> list = upload.parseRequest(request);
					if(list!=null){
						//5.循环所有项,
						for (FileItem item : list) {
							//判断file是否为普通表单文件,isFormField判断 是普通表单数据则返回true 否则返回false
							if(item.isFormField()){
								if("title".equals(item.getFieldName())){
									//如果找到对应属性名,直接获取对应的值
									dc.setTitle(item.getString("utf-8"));
								}
								if("remark".equals(item.getFieldName())){
									dc.setRemark(item.getString());
								}
							}else{
								//6.获取到要存储的文件夹
								String path ="D:/第二阶段项目/WebContent/ueditor/jsp/upload/file";
								System.out.println(path);
								
								//获取文件名
								String fileName = item.getName();
								if(fileName!=null&&fileName!="") {
								String filetype=fileName.substring(fileName.lastIndexOf(".")+1);
								System.out.println(filetype);
								if(filetype.equals("png")||filetype.equals("jpg")) {
									path="D:/第二阶段项目/WebContent/ueditor/jsp/upload/image";
								}
								File file = new File(path);
								//判断该路径是否存在,如果不存在就新建
								if(!file.exists()){
									file.mkdirs();
								}
								System.out.println(fileName);
								String fileBytes = fileName.substring(0, fileName.lastIndexOf("."))+System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
								System.out.println(fileBytes);
								File newFile = new File(file, fileBytes);
								//将item写出到指定文件中newFile
								item.write(newFile);
								
								
								//将文件名保存在对象中
								dc.setFileBytes(fileBytes);
								dc.setFileName(fileName);
								dc.setFiletype(filetype);
							}
								flag = true;
							}
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//普通提交方式获取
//				user.setUserName(request.getParameter("username"));
//				user.setPassword(request.getParameter("pwd"));
				flag = true;
			}
//			System.out.println("用户名:====="+user.getUserName());
//			System.out.println("密码为:====="+user.getPassword());
//			System.out.println("图片名为:====="+user.getUrl());
			if(flag){
				
				if(parameter!="") {
					int id=Integer.parseInt(parameter);
					dc.setId(id);
					Document findById = ds.findById(id);
					String fileBytes = findById.getFileBytes();
					String filetype = findById.getFiletype();
					String path ="D:/第二阶段项目/WebContent/ueditor/jsp/upload/file";
					if(filetype.equals("png")||filetype.equals("jpg")) {
						path="D:/第二阶段项目/WebContent/ueditor/jsp/upload/image";
					}
					String filePathAndName=path+File.separator+fileBytes;
					System.out.println(filePathAndName);
					delFile(filePathAndName);
					
				}
				
				ds.update(dc);
//				request.setAttribute("user", user);
				request.getRequestDispatcher("documentlist.action").forward(request, response);
			
			
			}}
		}
	
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
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
