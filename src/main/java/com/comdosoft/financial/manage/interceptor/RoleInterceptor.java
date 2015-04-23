/**
 * 
 */
package com.comdosoft.financial.manage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.SessionService;

/**
 * @author gookin.wu
 *
 * Email: gookin.wu@gmail.com
 * Date: 2015年4月20日 下午8:27:18
 */
public class RoleInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private SessionService sessionService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
		Customer c = sessionService.getLoginInfo(request);
		if(c.getTypes() == Customer.TYPE_SUPER){//超级管理员，拥有全部权限
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		HasRole hasRole = handlerMethod.getMethodAnnotation(HasRole.class);
		if(hasRole == null) {
			return true;
		}
		String[] roles = hasRole.value();
		for(String role : roles) {
			if(sessionService.hasRole(request,role)){
				return true;
			}
		}
		response.sendRedirect(request.getContextPath()+"/index?role=no");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null){
			modelAndView.addObject("Roles",new Roles(request,sessionService));
		}
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
			Customer c = sessionService.getLoginInfo(request);
			if(c.getTypes() == Customer.TYPE_SUPER){//超级管理员，拥有全部权限
				return true;
			}
			return sessionService.hasRole(request, role);
		}
	}
}
