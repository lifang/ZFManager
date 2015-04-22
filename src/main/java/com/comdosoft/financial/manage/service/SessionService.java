package com.comdosoft.financial.manage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.mapper.zhangfu.FactoryMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.MenuMapper;

@Service
public class SessionService {

    @Autowired
    private FactoryMapper factoryMapper;
    @Autowired
    private MenuMapper menuMapper;
	
	private static final String LOGIN_SESSION_KEY = "__LOGIN_KEY__";
    private static final String SESSION_FACTORY_KEY = "__SESSION_FACTORY_KEY__";
    private static final String SESSION_MENU_KEYS = "__SESSION_MENU_KEYS__";
    
	public boolean isLogged(HttpServletRequest request){
		return getLoginInfo(request)!=null;
	}

	public void storeLoginInfo(HttpServletRequest request,Customer customer) {
		request.getSession().setAttribute(LOGIN_SESSION_KEY, customer);
		List<String> menuKeys = menuMapper.customerMenuKeys(customer.getId());
		request.getSession().setAttribute(SESSION_MENU_KEYS, menuKeys);
        if(customer.getTypes() == Customer.TYPE_THIRD_PARTY){
            Factory factory = factoryMapper.findFactoryByCustomerId(customer.getId());
            request.getSession().setAttribute(SESSION_FACTORY_KEY, factory);
        }
	}
	
	public Customer getLoginInfo(HttpServletRequest request) {
		return (Customer)request.getSession().getAttribute(LOGIN_SESSION_KEY);
	}
	
	public boolean hasRole(HttpServletRequest request,String role) {
		List<String> menuKeys = (List<String>)request.getSession().getAttribute(SESSION_MENU_KEYS);
		if(menuKeys == null) {
			return false;
		}
		return menuKeys.contains(role);
	}

    public void clear(HttpServletRequest request){
		request.getSession().removeAttribute(LOGIN_SESSION_KEY);
	}
}
