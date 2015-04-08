package com.comdosoft.financial.manage.controller;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.service.GoodProfitService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
	
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        if(customer.getTypes() == Customer.TYPE_THIRD_PARTY){
            return "factory_role/index";
        }
        return "index";
	}
}
