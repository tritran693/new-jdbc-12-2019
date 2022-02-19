package com.laptrinhjavaweb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.utils.SessionUtil;

//Muốn chạy phải khai báo filter bên web.xml
public class AuthorizationFilter implements Filter {

	private ServletContext context;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.context = filterConfig.getServletContext();
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//Lấy nguyên cái link đang request
//		String url = rq.getRequestURI();  //Lấy hết
//		String test = rq.getContextPath();  //Lấy cái laptrinhjavaweb.com
		String url = rq.getServletPath(); //Link sau laptrinhjavaweb.com/admin-..
		if(url.startsWith("/admin")) {
			UserModel model = (UserModel) SessionUtil.getInstance().getValue(rq,"USERMODEL");
			if(model!=null) {
				if(model.getRole().getCode().equals(SystemConstant.ADMIN)) {
					chain.doFilter(request, response);
				}else if(model.getRole().getCode().equals(SystemConstant.USER)){
					resp.sendRedirect(rq.getContextPath() + "/dang-nhap?action=login&message=not_login&alert=danger");
				}
			}else {
				resp.sendRedirect(rq.getContextPath() + "/dang-nhap?action=login&message=not_permission&alert=danger");
			}
		}else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
