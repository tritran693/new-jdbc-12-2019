package com.laptrinhjavaweb.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.ICategoryService;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.sort.Sorter;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.MessageUtil;

//xài value thì chỉ xài được 1 cái HomeController thôi
// xài urlPatterns thì nhiều được
@WebServlet (urlPatterns =  {"/admin-new"})
public class NewController  extends HttpServlet{

	@Inject
	INewService newService;
	
	@Inject
	ICategoryService categoryService;
	
	//Sinh ra cho có nhưng cần thiết
	private static final long serialVersionUID = 2686801510274002166L;
	
	//Controller chỉ có nhiệm vụ set và đẩy đi thôi, ko có xử lý logic
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		NewModel model = new NewModel();
//		// Nhận value từ  trong url/input
//		String pageStr = req.getParameter("page");
//		String maxPageItemStr = req.getParameter("maxPageItem");
//		if(pageStr != null) {
//			model.setPage(Integer.parseInt(pageStr));
//		} else {
//			model.setPage(1);
//		}
//		if(maxPageItemStr != null) {
//			model.setMaxPageItem(Integer.parseInt(maxPageItemStr));
//		}
		//Ko xài cách trên vì mõi lần có 1 thuộc tính thì phải set :)) phiền lắm
		//Xài cái này nó sẽ lấy theo name và ánh xạ vào các biến trùng name đó
		NewModel model = FormUtil.toModel(NewModel.class, req);
		String view ="";
		if(model.getType().equals(SystemConstant.LIST)) {
		//Xài cách trên cho tự động ánh xạ, trong model sẽ có các attribute đó
		Pageble pageble = new PageRequest(model.getPage(), model.getMaxPageItem(),
														new Sorter(model.getSortName(), model.getSortBy()));
		model.setListResult(newService.findAll(pageble));
		model.setTotalItem(newService.getTotalItem());
		model.setTotalPage((int)Math.ceil((double) model.getTotalItem()/model.getMaxPageItem()));;
		
		view = "/views/admin/new/list.jsp";
		}else if(model.getType().equals(SystemConstant.EDIT)) {
			if(model.getId()!=null) {
				model = newService.findOne(model.getId());
			} 
			List<CategoryModel>list = categoryService.findAll();
			req.setAttribute("categories", list);
			view = "/views/admin/new/edit.jsp";
		}
		
		MessageUtil.showMessage(req);
		req.setAttribute(SystemConstant.MODEL, model);
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
