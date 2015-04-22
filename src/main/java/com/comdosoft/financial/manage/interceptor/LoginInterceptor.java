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
			modelAndView.addObject("logged_roles",new Roles(request,sessionService));
		}
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	public static class Roles {
		private HttpServletRequest request;
		private SessionService sessionService;
		
		/**
		 * @param request
		 * @param sessionService
		 */
		public Roles(HttpServletRequest request, SessionService sessionService) {
			super();
			this.request = request;
			this.sessionService = sessionService;
		}
		
		public boolean hasRole(String role){
			return sessionService.hasRole(request, role);
		}
	}

}
