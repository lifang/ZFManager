/**
 * 
 */
package com.comdosoft.financial.manage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
		return false;
	}

}
