package com.comdosoft.financial.manage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.comdosoft.financial.manage.service.SessionService;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Value("${url.login}")
	private String loginUrl;
	@Autowired
	private SessionService sessionService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean logined = sessionService.isLogged(request);
		if(!logined){
			response.sendRedirect(request.getContextPath()+loginUrl);
		}
		return logined;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null){
			modelAndView.addObject("logged_customer", sessionService.getLoginInfo(request));
		}
	}

}
