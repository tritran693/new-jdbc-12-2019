package com.laptrinhjavaweb.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//xài value thì chỉ xài được 1 cái HomeController thôi
// xài urlPatterns thì nhiều được
@WebServlet (urlPatterns =  {"/admin-home"})
public class HomeController  extends HttpServlet{

	//Sinh ra cho có nhưng cần thiết
	private static final long serialVersionUID = 2686801510274002166L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/home.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
