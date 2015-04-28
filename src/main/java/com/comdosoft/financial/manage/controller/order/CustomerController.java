package com.comdosoft.financial.manage.controller.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.CustomerService;

@Controller
@RequestMapping("/order/customer")
public class CustomerController extends BaseController {
	
	@Autowired
	private CustomerService customerService;

    @RequestMapping(value="search",method = RequestMethod.POST)
    public String search(HttpServletRequest request,String customerName,Model model, Integer agentId){
    	List<Customer> searchCustomer = customerService.searchCustomer(customerName,agentId);
		model.addAttribute("customers", searchCustomer);
        return "order/customerSearch";
    }
    
    @RequestMapping(value="saveOrUpdate",method = RequestMethod.POST)
    public String saveOrUpdate(HttpServletRequest request,Model model,String phone,String passport,
			String password,String repassword,Integer city) throws Exception{
    	if(!password.equals(repassword)){
    		throw new Exception("两次输入密码不同");
    	}
    	Customer customer = customerService.saveAndReturn(passport, password, phone, city);
    	model.addAttribute("customer", customer);
    	saveOperateRecord(request,OperateType.orderUserType, OperatePage.orderUserCreate, OperateAction.saveCustomer, customer.getId());
        return "order/customer";
    }
}
