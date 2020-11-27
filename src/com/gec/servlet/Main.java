package com.gec.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns={"/main.action","/left.action","/top.action","/right.action","/logout.action"})
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("main.action".equals(uri)){
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
		}
		if("left.action".equals(uri)){
			request.getRequestDispatcher("/WEB-INF/jsp/left.jsp").forward(request, response);
		}
		if("top.action".equals(uri)){
			request.getRequestDispatcher("/WEB-INF/jsp/top.jsp").forward(request, response);
		}
		if("right.action".equals(uri)){
			request.getRequestDispatcher("/WEB-INF/jsp/right.jsp").forward(request, response);
		}
		if("logout.action".equals(uri)) {
			response.sendRedirect("index.jsp");
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
