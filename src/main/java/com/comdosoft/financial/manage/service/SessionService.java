package com.comdosoft.financial.manage.service;

import javax.servlet.http.HttpServletRequest;

import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.mapper.zhangfu.FactoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;

@Service
public class SessionService {

    @Autowired
    private FactoryMapper factoryMapper;
	
	private static final String LOGIN_SESSION_KEY = "__LOGIN_KEY__";
    private static final String SESSION_FACTORY_KEY = "__SESSION_FACTORY_KEY__";
	public boolean isLogged(HttpServletRequest request){
		return getLoginInfo(request)!=null;
	}

	public void storeLoginInfo(HttpServletRequest request,Object obj) {
		request.getSession().setAttribute(LOGIN_SESSION_KEY, obj);
        Customer customer = (Customer)obj;
        if(customer.getTypes() == Customer.TYPE_THIRD_PARTY){
            Factory factory = factoryMapper.findFactoryByCustomerId(customer.getId());
            request.getSession().setAttribute(SESSION_FACTORY_KEY, factory);
        }
	}
	
	public Customer getLoginInfo(HttpServletRequest request) {
		return (Customer)request.getSession().getAttribute(LOGIN_SESSION_KEY);
	}

    public void clear(HttpServletRequest request){
		request.getSession().removeAttribute(LOGIN_SESSION_KEY);
	}
}
