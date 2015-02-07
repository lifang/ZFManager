package com.comdosoft.financial.manage.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;

@Service
public class SessionService {
	
	private static final String LOGIN_SESSION_KEY = "__LOGIN_KEY__";
	
	public boolean isLogged(HttpServletRequest request){
		return getLoginInfo(request)!=null;
	}

	public void storeLoginInfo(HttpServletRequest request,Object obj) {
		request.getSession().setAttribute(LOGIN_SESSION_KEY, obj);
	}
	
	public Customer getLoginInfo(HttpServletRequest request) {
		return (Customer)request.getSession().getAttribute(LOGIN_SESSION_KEY);
	}
	
	public void clear(HttpServletRequest request){
		request.getSession().removeAttribute(LOGIN_SESSION_KEY);
	}
}
